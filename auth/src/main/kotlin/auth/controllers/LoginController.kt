package com.sushobh.auth.controllers

import com.sushobh.auth.entity.SignupAttempt
import com.sushobh.auth.repository.SignupAttemptRepo
import com.sushobh.common.util.DateUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LoginController(val signupAttemptRepo: SignupAttemptRepo, val dateUtil: DateUtil) {
    data class LoginApiResponse(
            val token: String?,
            val isSuccessful: Boolean,
            val message: String,
            val code: String
    )

    data class LoginInput(val username : String, val password : String)

    data class SignupInput(val email : String,val password: String)

    @PostMapping("/login")
    suspend fun login(@RequestBody loginInput: LoginInput) : LoginApiResponse {
        return LoginApiResponse("asdkasjgo23487asogdhgaskudygasd",true,"Failed bro","SUCCESS")
    }

    @PostMapping("/signup")
    suspend fun signup(@RequestBody signupInput: SignupInput) : MutableIterable<SignupAttempt> {
        val signupAttempt = SignupAttempt()
        signupAttempt.time = dateUtil.getCurrentTime()
        signupAttempt.password = signupInput.password
        signupAttempt.email = signupInput.email
        signupAttemptRepo.save(signupAttempt)
        return signupAttemptRepo.findAll()
    }

}