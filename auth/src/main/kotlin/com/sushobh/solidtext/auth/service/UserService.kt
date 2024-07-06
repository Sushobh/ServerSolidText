package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.OTP_TYPE_SIGNUP
import com.sushobh.solidtext.auth.SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS
import com.sushobh.solidtext.auth.entity.*
import com.sushobh.solidtext.auth.repository.ETPasswordRepo
import com.sushobh.solidtext.auth.repository.ETUserRepo
import com.sushobh.solidtext.auth.repository.ETUserTokenPairRepo
import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
import common.util.time.SecondsExpirable
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull


@Component
class UserService(
    private val signupAttemptRepo: SignupAttemptRepo,
    private val etPasswordRepo: ETPasswordRepo,
    private val etUserRepo: ETUserRepo,
    private val otpService: OtpService,
    private val dateUtil: DateUtil,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val tokenService: TokenService,
    private val tokenPairRepo: ETUserTokenPairRepo,
    @Qualifier("loginTokenConfig") private val loginTokenConfig: TokenService.TokenConfig
) {

    sealed class SignupStatus(val text: String) {
        data object TooManyRequestsForEmail : SignupStatus("TooManyRequestsForEmail")
        data object OtpSent : SignupStatus("OtpSent")
        data object UserAlreadyExists : SignupStatus("UserAlreadyExists")
    }

    sealed class OtpValidateStatus(val text: String) {
        data object UserCreated : OtpValidateStatus("UserCreated")
        data object ExpiredRequest : OtpValidateStatus("ExpiredRequest")
        data object InvalidDetails : OtpValidateStatus("InvalidDetails")
    }

    sealed class LoginStatus() {
        data object InvalidCredentials : LoginStatus()
        data class Success(val tokenText: String) : LoginStatus()
    }

    data class LoginInput(val email: String, val password: String)
    data class SignupInput(val email: String, val password: String)
    data class OtpValidateInput(val otpText: String, val email: String)

    private fun doesUserExist(email: String): Boolean {
        return etUserRepo.findByEmail(email) != null
    }

    fun onSignupAttempt(input: SignupInput): SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if (doesUserExist) {
            return SignupStatus.UserAlreadyExists
        } else {
            val sentOtp = otpService.sendOtp(OTP_TYPE_SIGNUP)
            val signupAttempt =
                ETSignupAttempt(
                    input.email,
                    passwordEncoder.encode(input.password),
                    dateUtil.getCurrentTime(),
                    sentOtp.id
                )
            signupAttemptRepo.save(signupAttempt)
            return SignupStatus.OtpSent
        }
    }

    fun validateOtp(otpValidateInput: OtpValidateInput): OtpValidateStatus {
        val latestAttempt = signupAttemptRepo.findLatestByEmail(otpValidateInput.email)
        latestAttempt?.let {
            val hasExpired = dateUtil.hasExpired(SecondsExpirable(latestAttempt.time, SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS))
            if (hasExpired) {
                return OtpValidateStatus.ExpiredRequest
            }
            latestAttempt.otpId?.let {
                val sentOtp = otpService.getOtp(it)
                sentOtp?.let {
                    val time = dateUtil.getCurrentTime()
                    if (otpValidateInput.otpText == sentOtp.otp) {
                        val etPassword =
                            etPasswordRepo.save(ETPassword(time = time, passwordText = latestAttempt.password))
                        val etUser = ETUser(
                            time = time, username = latestAttempt.email.split("@")[0],
                            passwordId = etPassword.id, email = latestAttempt.email
                        )
                        etUserRepo.save(etUser)
                        return OtpValidateStatus.UserCreated
                    }
                }
            }
        }
        return OtpValidateStatus.InvalidDetails
    }

    fun login(loginInput: LoginInput): LoginStatus {
        val etUser = etUserRepo.findByEmail(loginInput.email)
        etUser?.let {
            val etPassword = etPasswordRepo.findById(etUser.passwordId).getOrNull()
            etPassword?.let {
                if (passwordEncoder.matches(loginInput.password, etPassword.passwordText)) {
                    val token = tokenService.generateToken(loginTokenConfig)
                    tokenPairRepo.save(ETUserTokenPair(ETUserTokenPairId(etUser.id, token.id)))
                    return LoginStatus.Success(token.tokenText)
                }
            }
        }
        return LoginStatus.InvalidCredentials
    }

    fun getUserFromToken(tokenText: String): ETUser? {
        try {
            val token = tokenService.validateToken(tokenText)
            val user = etUserRepo.findUserByToken(token.id)
            return user
        } catch (e: Exception) {
            return null
        }
    }
}