package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.*
import com.sushobh.solidtext.auth.OTP_TYPE_SIGNUP
import com.sushobh.solidtext.auth.SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS
import com.sushobh.solidtext.auth.USER_PROP_VALUE_MAX_LENGTH
import com.sushobh.solidtext.auth.entity.*
import com.sushobh.solidtext.auth.repository.ETPasswordRepo
import com.sushobh.solidtext.auth.repository.ETUserRepo
import com.sushobh.solidtext.auth.repository.ETUserTokenPairRepo
import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
import com.sushobh.solidtext.auth.toStUser
import common.util.time.SecondsExpirable
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.math.BigInteger
import kotlin.jvm.optionals.getOrNull


@Component
internal class UserService internal constructor(
    private val signupAttemptRepo: SignupAttemptRepo,
    private val etPasswordRepo: ETPasswordRepo,
    private val etUserRepo: ETUserRepo,
    private val otpService: OtpService,
    private val dateUtil: DateUtil,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenService: TokenService,
    private val tokenPairRepo: ETUserTokenPairRepo,
    private val entityManager: EntityManager,
    @Qualifier("userPropKeys") private val userPropKeys : Set<String>,
    @Qualifier("loginTokenConfig") private val loginTokenConfig: TokenService.TokenConfig
) {


    private fun doesUserExist(email: String): Boolean {
        return etUserRepo.findByEmail(email) != null
    }



    fun getUserByName(searchUserInput: AuthServiceInput.SearchUserInput) : AuthServiceOutput.SearchUserStatus {
        val etUser = etUserRepo.findUserByName(searchUserInput.userName)
        etUser?.let {
            return AuthServiceOutput.SearchUserStatus.Found(it.toStUser())
        }
        return AuthServiceOutput.SearchUserStatus.UserNotFound()
    }

    fun onSignupAttempt(input: AuthServiceInput.SignupInput): AuthServiceOutput.SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if (doesUserExist) {
            return AuthServiceOutput.SignupStatus.UserAlreadyExists()
        } else {
            val sentOtp = otpService.sendOtp(OTP_TYPE_SIGNUP)
            sentOtp?.let {
                val signupAttempt =
                    ETSignupAttempt(
                        input.email,
                        passwordEncoder.encode(input.password),
                        dateUtil.getCurrentTime(),
                        sentOtp.id
                    )
                signupAttemptRepo.save(signupAttempt)
                return AuthServiceOutput.SignupStatus.OtpSent(sentOtp.stringid.orEmpty())
            }
            return AuthServiceOutput.SignupStatus.Error()
        }
    }

    fun validateOtp(otpValidateInput: AuthServiceInput.OtpValidateInput): AuthServiceOutput.OtpValidateStatus {
        val latestAttempt = signupAttemptRepo.findByOtpStringId(otpValidateInput.otpId)
        latestAttempt?.let {
            val hasExpired = dateUtil.hasExpired(SecondsExpirable(latestAttempt.time, SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS))
            if (hasExpired) {
                return AuthServiceOutput.OtpValidateStatus.ExpiredRequest()
            }
            latestAttempt.otpId?.let {
                val sentOtp = otpService.getOtp(it)
                sentOtp?.let {
                    if(otpValidateInput.otpId != sentOtp.stringid){
                        return AuthServiceOutput.OtpValidateStatus.InvalidDetails()
                    }
                    val time = dateUtil.getCurrentTime()
                    if (otpValidateInput.otpText == sentOtp.otp) {
                        val etPassword =
                            etPasswordRepo.save(ETPassword(time = time, passwordText = latestAttempt.password))
                        val etUser = ETUser(
                            time = time, username = latestAttempt.email.split("@")[0],
                            passwordId = etPassword.id, email = latestAttempt.email
                        )
                        etUserRepo.save(etUser)
                        return AuthServiceOutput.OtpValidateStatus.Success()
                    }
                }
            }
        }
        return AuthServiceOutput.OtpValidateStatus.InvalidDetails()
    }

    fun login(loginInput: AuthServiceInput.LoginInput): AuthServiceOutput.LoginStatus {
        val etUser = etUserRepo.findByEmail(loginInput.email)
        etUser?.let {
            val etPassword = etPasswordRepo.findById(etUser.passwordId).getOrNull()
            etPassword?.let {
                if (passwordEncoder.matches(loginInput.password, etPassword.passwordText)) {
                    val token = tokenService.generateToken(loginTokenConfig)
                    tokenPairRepo.save(ETUserTokenPair(ETUserTokenPairId(etUser.id, token.id)))
                    return AuthServiceOutput.LoginStatus.Success(token.tokenText)
                }
            }
        }
        return AuthServiceOutput.LoginStatus.InvalidCredentials()
    }

    internal fun getUserFromToken(tokenText: String): ETUser? {
        try {
            val token = tokenService.validateToken(tokenText)
            val user = etUserRepo.findUserByToken(token.id)
            return user
        } catch (e: Exception) {
            return null
        }
    }

    fun updateUserName(updateUserNameInput: AuthServiceInput.UpdateUserNameInput, user: STUser) : AuthServiceOutput.UpdateUserNameStatus {
        val etUser = etUserRepo.findById(user.userId).getOrNull()
        etUser?.let {
              etUserRepo.updateUserName(updateUserNameInput.newName,etUser.id)
              val newUserRow = etUserRepo.findById(user.userId).getOrNull()
              newUserRow?.let {
                  return AuthServiceOutput.UpdateUserNameStatus.Success(RespETUser(emailId = newUserRow.email, userName = newUserRow.username, userId = newUserRow.id))
              }
        }
        return AuthServiceOutput.UpdateUserNameStatus.Failed()
    }

    fun updateUserProp(input: AuthServiceInput.UserPropInput, extra: STUser) : AuthServiceOutput.UpdateUserPropStatus {
        if(!userPropKeys.contains(input.key) || input.value.orEmpty().length > USER_PROP_VALUE_MAX_LENGTH){
            return AuthServiceOutput.UpdateUserPropStatus.Failed("Invalid key or value")
        }
        etUserRepo.updateUserProp(input.key,input.value.orEmpty(),extra.userId)
        entityManager.clear()
        return AuthServiceOutput.UpdateUserPropStatus.Success(getUserById(extra.userId))
    }

    fun getUserById(id : BigInteger) : STUser? {
        return this.etUserRepo.findById(id).getOrNull()?.toStUser()
    }

    fun getUserProps( extra: STUser): AuthServiceOutput.GetUserPropsStatus {
          val user : ETUser? = this.etUserRepo.findById(extra.userId).getOrNull()
          user?.let {
              val listOfProps = ArrayList<UserProp>()
              user.userProps?.keys?.forEach { key ->
                  if(userPropKeys.contains(key)){
                      listOfProps.add(UserProp(key,user.userProps.get(key),true))
                  }
              }
              return AuthServiceOutput.GetUserPropsStatus.Success(listOfProps)
          }

        return AuthServiceOutput.GetUserPropsStatus.Failed("User not found")
    }


}