package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.apiclasses.AuthServiceClasses
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.getDefaultRequestChain
import com.sushobh.solidtext.auth.getUserRequestChain
import com.sushobh.solidtext.auth.service.TokenService
import com.sushobh.solidtext.auth.service.UserService
import common.util.requests.STResponse
import org.springframework.web.bind.annotation.*


@RestController
internal class SignupController(
    private val userService: UserService,
    private val tokenService: TokenService
) {

    @PostMapping("/public/signup")
    suspend fun signup(@RequestBody body: AuthServiceClasses.SignupInput): STResponse<AuthServiceClasses.SignupStatus> {
        return getDefaultRequestChain<AuthServiceClasses.SignupInput, AuthServiceClasses.SignupStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.onSignupAttempt(input.requestBody!!),
                    null
                )
            }.next()
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: AuthServiceClasses.OtpValidateInput): STResponse<AuthServiceClasses.OtpValidateStatus> {
        return getDefaultRequestChain<AuthServiceClasses.OtpValidateInput, AuthServiceClasses.OtpValidateStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.validateOtp(input.requestBody!!),
                    null
                )
            }.next()
    }

    @PostMapping("/public/login")
    suspend fun login(
        @RequestBody body: AuthServiceClasses.LoginInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceClasses.LoginStatus> {
        return getDefaultRequestChain<AuthServiceClasses.LoginInput, AuthServiceClasses.LoginStatus>(body, headers)
            .addItem { input, chain ->
                val result = userService.login(body)
                STResponse(result, null)
            }.next()
    }

    @PostMapping("/user/updateUserName")
    suspend fun updateUserName(@RequestBody body : AuthServiceClasses.UpdateUserNameInput,
                               @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceClasses.UpdateUserNameStatus> {
        return getUserRequestChain<AuthServiceClasses.UpdateUserNameInput, AuthServiceClasses.UpdateUserNameStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserName(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @PostMapping("/user/updateProperty")
    suspend fun updateProperty(@RequestBody body : AuthServiceClasses.UserPropInput,
                               @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceClasses.UpdateUserPropStatus> {
        return getUserRequestChain<AuthServiceClasses.UserPropInput, AuthServiceClasses.UpdateUserPropStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserProp(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @PostMapping("/user/searchUserByName")
    suspend fun searchUserByName(@RequestBody body : AuthServiceClasses.SearchUserInput,
                                 @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceClasses.SearchUserStatus> {
        return getUserRequestChain<AuthServiceClasses.SearchUserInput, AuthServiceClasses.SearchUserStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.getUserByName(body), null)
            }.next()
    }

}