package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.repository.SignupAttemptRepo
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



    @PostMapping("public/login")
    suspend fun login(@RequestBody loginInput: LoginInput) : LoginApiResponse {
        return LoginApiResponse("asdkasjgo23487asogdhgaskudygasd",true,"Failed bro","SUCCESS")
    }


}