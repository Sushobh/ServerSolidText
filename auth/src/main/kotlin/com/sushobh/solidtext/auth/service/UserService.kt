package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.apiclasses.AuthServiceClasses
import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.OTP_TYPE_SIGNUP
import com.sushobh.solidtext.auth.SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS
import com.sushobh.solidtext.auth.USER_PROP_VALUE_MAX_LENGTH
import com.sushobh.solidtext.auth.entity.*
import com.sushobh.solidtext.auth.repository.ETPasswordRepo
import com.sushobh.solidtext.auth.repository.ETUserRepo
import com.sushobh.solidtext.auth.repository.ETUserTokenPairRepo
import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
import com.sushobh.solidtext.apiclasses.RespETUser
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



    fun getUserByName(searchUserInput: AuthServiceClasses.SearchUserInput) : AuthServiceClasses.SearchUserStatus {
        val etUser = etUserRepo.findUserByName(searchUserInput.userName)
        etUser?.let {
            return AuthServiceClasses.SearchUserStatus.Found(it.toStUser())
        }
        return AuthServiceClasses.SearchUserStatus.UserNotFound
    }

    fun onSignupAttempt(input: AuthServiceClasses.SignupInput): AuthServiceClasses.SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if (doesUserExist) {
            return AuthServiceClasses.SignupStatus.UserAlreadyExists
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
                return AuthServiceClasses.SignupStatus.OtpSent(sentOtp.stringid.orEmpty())
            }
            return AuthServiceClasses.SignupStatus.Error
        }
    }

    fun validateOtp(otpValidateInput: AuthServiceClasses.OtpValidateInput): AuthServiceClasses.OtpValidateStatus {
        val latestAttempt = signupAttemptRepo.findByOtpStringId(otpValidateInput.otpId)
        latestAttempt?.let {
            val hasExpired = dateUtil.hasExpired(SecondsExpirable(latestAttempt.time, SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS))
            if (hasExpired) {
                return AuthServiceClasses.OtpValidateStatus.ExpiredRequest
            }
            latestAttempt.otpId?.let {
                val sentOtp = otpService.getOtp(it)
                sentOtp?.let {
                    if(otpValidateInput.otpId != sentOtp.stringid){
                        return AuthServiceClasses.OtpValidateStatus.InvalidDetails
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
                        return AuthServiceClasses.OtpValidateStatus.Success
                    }
                }
            }
        }
        return AuthServiceClasses.OtpValidateStatus.InvalidDetails
    }

    fun login(loginInput: AuthServiceClasses.LoginInput): AuthServiceClasses.LoginStatus {
        val etUser = etUserRepo.findByEmail(loginInput.email)
        etUser?.let {
            val etPassword = etPasswordRepo.findById(etUser.passwordId).getOrNull()
            etPassword?.let {
                if (passwordEncoder.matches(loginInput.password, etPassword.passwordText)) {
                    val token = tokenService.generateToken(loginTokenConfig)
                    tokenPairRepo.save(ETUserTokenPair(ETUserTokenPairId(etUser.id, token.id)))
                    return AuthServiceClasses.LoginStatus.Success(token.tokenText)
                }
            }
        }
        return AuthServiceClasses.LoginStatus.InvalidCredentials
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

    fun updateUserName(updateUserNameInput: AuthServiceClasses.UpdateUserNameInput, user: STUser) : AuthServiceClasses.UpdateUserNameStatus {
        val etUser = etUserRepo.findById(user.userId).getOrNull()
        etUser?.let {
              etUserRepo.updateUserName(updateUserNameInput.newName,etUser.id)
              val newUserRow = etUserRepo.findById(user.userId).getOrNull()
              newUserRow?.let {
                  return AuthServiceClasses.UpdateUserNameStatus.Success(RespETUser(emailId = newUserRow.email, userName = newUserRow.username, userId = newUserRow.id))
              }
        }
        return AuthServiceClasses.UpdateUserNameStatus.Failed
    }

    fun updateUserProp(input: AuthServiceClasses.UserPropInput, extra: STUser) : AuthServiceClasses.UpdateUserPropStatus {
        if(!userPropKeys.contains(input.key) || input.value.orEmpty().length > USER_PROP_VALUE_MAX_LENGTH){
            return AuthServiceClasses.UpdateUserPropStatus.Failed("Invald key or value")
        }
        etUserRepo.updateUserProp(input.key,input.value.orEmpty(),extra.userId)
        entityManager.clear()
        return AuthServiceClasses.UpdateUserPropStatus.Success(getUserById(extra.userId))
    }

    fun getUserById(id : BigInteger) : STUser? {
        return this.etUserRepo.findById(id).getOrNull()?.toStUser()
    }


}