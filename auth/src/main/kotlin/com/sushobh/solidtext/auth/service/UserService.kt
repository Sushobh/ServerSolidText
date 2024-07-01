package com.sushobh.solidtext.auth.service

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.sushobh.solidtext.auth.OTP_TYPE_SIGNUP
import com.sushobh.solidtext.auth.entity.ETSignupAttempt
import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
import com.sushobh.common.util.DateUtil
import org.springframework.stereotype.Component


@Component
class UserService(val signupAttemptRepo: SignupAttemptRepo, val otpSender: OtpSender, val dateUtil: DateUtil) {

    sealed class SignupStatus {
        data object TooManyRequestsForEmail : SignupStatus()
        data object OtpSent : SignupStatus()
        data object UserAlreadyExists  : SignupStatus()
    }

    data class SignupInput(val email : String,val password: String)

    fun doesUserExist(email : String) : Boolean{
        return false
    }

    fun onSignupAttempt(input: SignupInput) : SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if(doesUserExist){
            return SignupStatus.UserAlreadyExists
        }
        else {
            val sentOtp = otpSender.sendOtp(OTP_TYPE_SIGNUP)
            val signupAttempt =
                ETSignupAttempt(input.email,input.password,dateUtil.getCurrentTime(),sentOtp.id)
            signupAttemptRepo.save(signupAttempt)
            return SignupStatus.OtpSent
        }
    }
}