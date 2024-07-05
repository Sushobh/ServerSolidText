package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.service.UserService
import common.util.requests.ChainItem
import common.util.requests.RequestChain
import common.util.requests.STRequest

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

    val justALogger : ChainItem<UserService.SignupInput,SignupResponse> = ChainItem {x,y ->
        println(x.toString())
        y.next()
    }

    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody body: UserService.SignupInput): SignupResponse {
        return RequestChain.new<UserService.SignupInput,SignupResponse>(STRequest(body))
            .addItem(justALogger)
            .addItem { input, chain -> SignupResponse(userService.onSignupAttempt(input.requestBody)) }.next()
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: UserService.OtpValidateInput): OtpValidateResponse {
        return RequestChain.new<UserService.OtpValidateInput,OtpValidateResponse>(STRequest(body))
            .addItem { input, chain -> OtpValidateResponse(userService.validateOtp(input.requestBody)) }.next()

    }

    @PostMapping("/public/login")
    suspend fun login(@RequestBody body : UserService.LoginInput) : LoginResponse {
        return RequestChain.new<UserService.LoginInput,LoginResponse>(STRequest(body))
            .addItem { input,chain ->
                chain.next()
            }
            .addItem { input, chain ->
                val result = userService.login(body)
                LoginResponse(result)
            }.next()
    }

}