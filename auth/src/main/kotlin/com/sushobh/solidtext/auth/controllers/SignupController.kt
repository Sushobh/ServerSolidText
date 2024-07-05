package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.service.UserService

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class SignupController(
    val userService: UserService
) {

    data class SignupResponse(val signupStatus: UserService.SignupStatus)
    data class OtpValidateResponse(val status: UserService.OtpValidateStatus)
    data class LoginResponse(val status : UserService.LoginStatus)

    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody signupInput: UserService.SignupInput): SignupResponse {
        val result = userService.onSignupAttempt(signupInput)
        return SignupResponse(result)
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: UserService.OtpValidateInput): OtpValidateResponse {
        val result = userService.validateOtp(body)
        return OtpValidateResponse(result)
    }

    @PostMapping("/public/login")
    suspend fun login(@RequestBody body : UserService.LoginInput) : LoginResponse {
        val result = userService.login(body)
        return LoginResponse(result)
    }

}