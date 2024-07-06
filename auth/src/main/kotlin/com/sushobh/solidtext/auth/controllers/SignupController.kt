package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.AUTH_HEADER
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.entity.ETUser
import com.sushobh.solidtext.auth.service.TokenService
import com.sushobh.solidtext.auth.service.UserService
import com.sushobh.solidtext.auth.service.UserTokenChecker
import common.util.requests.RequestChain
import common.util.requests.STRequest
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.*


@RestController
class SignupController(
    private val userService: UserService,
    private val tokenService: TokenService
) {


    data class SignupResponse(val signupStatus: UserService.SignupStatus)
    data class OtpValidateResponse(val status: UserService.OtpValidateStatus)
    data class LoginResponse(val status: UserService.LoginStatus)


    private fun <X, Y> getDefaultRequestChain(
        body: X?,
        headers: Map<String, String> = hashMapOf()
    ): RequestChain<X, Y> {
        return RequestChain.new(STRequest(headers, body))
    }

    private fun <X, Y> getUserRequestChain(body: X?, headers: Map<String, String> = hashMapOf()): RequestChain<X, Y> {
        return getDefaultRequestChain<X, Y>(body, headers)
            .addItem(UserTokenChecker(userService))
    }


    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody body: UserService.SignupInput): STResponse<SignupResponse> {
        return getDefaultRequestChain<UserService.SignupInput, SignupResponse>(body)
            .addItem { input, chain ->
                STResponse(
                    SignupResponse(userService.onSignupAttempt(input.requestBody!!)),
                    null
                )
            }.next()
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: UserService.OtpValidateInput): STResponse<OtpValidateResponse> {
        return getDefaultRequestChain<UserService.OtpValidateInput, OtpValidateResponse>(body)
            .addItem { input, chain ->
                STResponse(
                    OtpValidateResponse(userService.validateOtp(input.requestBody!!)),
                    null
                )
            }.next()

    }

    @PostMapping("/public/login")
    suspend fun login(
        @RequestBody body: UserService.LoginInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<LoginResponse> {
        return getDefaultRequestChain<UserService.LoginInput, LoginResponse>(body, headers)
            .addItem { input, chain ->
                val result = userService.login(body)
                STResponse(LoginResponse(result), null)
            }.next()
    }

    @PostMapping("/user/updateUserName")
    suspend fun updateUserName(@RequestBody body : UserService.UpdateUserNameInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<UserService.UpdateUserNameStatus> {
        return getUserRequestChain<UserService.UpdateUserNameInput, UserService.UpdateUserNameStatus>(null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserName(body,input.getExtra<ETUser>(EXTRA_USER).id), null)
            }.next()
    }

}