package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.service.UserService

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class SignupController(val userService: UserService
) {

    data class SignupResponse(val signupStatus: UserService.SignupStatus)



    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody signupInput: UserService.SignupInput) : SignupResponse {
        val result = userService.onSignupAttempt(signupInput)
        return SignupResponse(result)
    }

}