package com.sushobh.solidtext.auth.service

import com.sushobh.solidtext.auth.OTP_TYPE_SIGNUP
import com.sushobh.solidtext.auth.entity.ETSignupAttempt
import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS
import common.util.time.SecondsExpirable
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.temporal.ChronoUnit


@Component
class UserService(val signupAttemptRepo: SignupAttemptRepo, val otpService: OtpService, val dateUtil: DateUtil) {

    sealed class SignupStatus(val text : String) {
        data object TooManyRequestsForEmail : SignupStatus("TooManyRequestsForEmail")
        data object OtpSent : SignupStatus("OtpSent")
        data object UserAlreadyExists  : SignupStatus("UserAlreadyExists")
    }

    sealed class OtpValidateStatus(val text : String) {
       data object UserCreated : OtpValidateStatus("UserCreated")
       data object ExpiredRequest : OtpValidateStatus("ExpiredRequest")
       data object InvalidDetails : OtpValidateStatus("InvalidDetails")
    }

    data class SignupInput(val email : String,val password: String)

    data class OtpValidateInput(val otpText : String,val email : String)

    private fun doesUserExist(email : String) : Boolean {
        //TODO implementation
        return false
    }

    fun onSignupAttempt(input: SignupInput) : SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if(doesUserExist){
            return SignupStatus.UserAlreadyExists
        }
        else {
            val sentOtp = otpService.sendOtp(OTP_TYPE_SIGNUP)
            val signupAttempt =
                ETSignupAttempt(input.email,input.password,dateUtil.getCurrentTime(),sentOtp.id)
            signupAttemptRepo.save(signupAttempt)
            return SignupStatus.OtpSent
        }
    }

    fun validateOtp(otpValidateInput: OtpValidateInput) : OtpValidateStatus {
        val latestAttempt = signupAttemptRepo.findLatestByEmail(otpValidateInput.email)

        latestAttempt?.let {
            val hasExpired = dateUtil.hasExpired(SecondsExpirable(latestAttempt.time, SIGNUP_ATTEMPT_EXPIRY_IN_SECONDS))
            if(hasExpired){
                return OtpValidateStatus.ExpiredRequest
            }
            latestAttempt.otpId?.let {
               val sentOtp = otpService.getOtp(it)
               sentOtp?.let {
                   if(otpValidateInput.otpText == sentOtp.otp){
                       return OtpValidateStatus.UserCreated
                   }
               }
            }
        }
        return OtpValidateStatus.InvalidDetails
    }
}