package com.sushobh.auth.service

import com.sushobh.auth.controllers.SignupController
import com.sushobh.auth.entity.SignupAttempt
import com.sushobh.auth.repository.SignupAttemptRepo
import com.sushobh.auth.util.OtpGenerator
import com.sushobh.common.util.DateUtil
import org.springframework.stereotype.Service


@Service
class UserService(val signupAttemptRepo: SignupAttemptRepo,val dateUtil: DateUtil) {

    sealed class SignupStatus {
        object TooManyRequestsForEmail : SignupStatus()
        object OtpSent : SignupStatus()
        object UserAlreadyExists  : SignupStatus()
    }

    data class SignupInput(val email : String,val password: String)

    fun doesUserExist(email : String) : Boolean{
        return true
    }

    fun onSignupAttempt(input: SignupInput) : SignupStatus {
        val doesUserExist = doesUserExist(input.email)
        if(doesUserExist){
            return SignupStatus.UserAlreadyExists
        }
        else {
            val signupAttempt =
                SignupAttempt(input.email,input.password,dateUtil.getCurrentTime())
            signupAttemptRepo.save(signupAttempt)
            return SignupStatus.OtpSent
        }
    }

}