package com.sushobh.auth.controllers

import com.sushobh.auth.entity.SignupAttempt
import com.sushobh.auth.repository.SignupAttemptRepo
import com.sushobh.auth.service.UserService
import com.sushobh.auth.util.OtpGenerator
import com.sushobh.common.util.DateUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class SignupController(val userService: UserService,
                       val otpGenerator: OtpGenerator) {

    data class SignupResponse(val signupStatus: UserService.SignupStatus)


    @GetMapping("/public/hello")
    fun sayHello() : String{
        return "Hey Sushobh"
    }

    @GetMapping("/user/hello")
    fun sayHello2() : String{
        return "Hey Sushobh"
    }


    @GetMapping("/trump/hello")
    fun sayHello3() : String{
        return "Hey Sushobh"
    }

    @PostMapping("/signup")
    suspend fun signup(@RequestBody signupInput: UserService.SignupInput) : SignupResponse {
        val result = userService.onSignupAttempt(signupInput)
        return SignupResponse(result)
    }

}