package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.auth.AUTH_HEADER
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.entity.ETUser
import com.sushobh.solidtext.auth.getDefaultRequestChain
import com.sushobh.solidtext.auth.getUserRequestChain
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





    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody body: UserService.SignupInput): STResponse<UserService.SignupStatus> {
        return getDefaultRequestChain<UserService.SignupInput, UserService.SignupStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.onSignupAttempt(input.requestBody!!),
                    null
                )
            }.next()
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: UserService.OtpValidateInput): STResponse<UserService.OtpValidateStatus> {
        return getDefaultRequestChain<UserService.OtpValidateInput, UserService.OtpValidateStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.validateOtp(input.requestBody!!),
                    null
                )
            }.next()

    }

    @PostMapping("/public/login")
    suspend fun login(
        @RequestBody body: UserService.LoginInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<UserService.LoginStatus> {
        return getDefaultRequestChain<UserService.LoginInput, UserService.LoginStatus>(body, headers)
            .addItem { input, chain ->
                val result = userService.login(body)
                STResponse(result, null)
            }.next()
    }

    @PostMapping("/user/updateUserName")
    suspend fun updateUserName(@RequestBody body : UserService.UpdateUserNameInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<UserService.UpdateUserNameStatus> {
        return getUserRequestChain<UserService.UpdateUserNameInput, UserService.UpdateUserNameStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserName(body,input.getExtra<ETUser>(EXTRA_USER).id), null)
            }.next()
    }


}