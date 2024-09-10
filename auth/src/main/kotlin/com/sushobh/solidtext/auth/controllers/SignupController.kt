package com.sushobh.solidtext.auth.controllers

import com.sushobh.solidtext.apiclasses.AuthServiceOutput
import com.sushobh.solidtext.apiclasses.AuthServiceInput
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
    suspend fun signup(@RequestBody body: AuthServiceInput.SignupInput): STResponse<AuthServiceOutput.SignupStatus> {
        return getDefaultRequestChain<AuthServiceInput.SignupInput, AuthServiceOutput.SignupStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.onSignupAttempt(input.requestBody!!),
                    null
                )
            }.next()
    }

    @PostMapping("/public/otpValidate")
    suspend fun otpValidate(@RequestBody body: AuthServiceInput.OtpValidateInput): STResponse<AuthServiceOutput.OtpValidateStatus> {
        return getDefaultRequestChain<AuthServiceInput.OtpValidateInput, AuthServiceOutput.OtpValidateStatus>(body)
            .addItem { input, chain ->
                STResponse(
                    userService.validateOtp(input.requestBody!!),
                    null
                )
            }.next()
    }

    @PostMapping("/public/login")
    suspend fun login(
        @RequestBody body: AuthServiceInput.LoginInput,
        @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceOutput.LoginStatus> {
        return getDefaultRequestChain<AuthServiceInput.LoginInput, AuthServiceOutput.LoginStatus>(body, headers)
            .addItem { input, chain ->
                val result = userService.login(body)
                STResponse(result, null)
            }.next()
    }

    @PostMapping("/user/updateUserName")
    suspend fun updateUserName(@RequestBody body : AuthServiceInput.UpdateUserNameInput,
                               @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceOutput.UpdateUserNameStatus> {
        return getUserRequestChain<AuthServiceInput.UpdateUserNameInput, AuthServiceOutput.UpdateUserNameStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserName(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }

    @PostMapping("/user/updateProperty")
    suspend fun updateProperty(@RequestBody body : AuthServiceInput.UserPropInput,
                               @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceOutput.UpdateUserPropStatus> {
        return getUserRequestChain<AuthServiceInput.UserPropInput, AuthServiceOutput.UpdateUserPropStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.updateUserProp(body,input.getExtra(EXTRA_USER)), null)
            }.next()
    }


    @PostMapping("/user/searchUserByName")
    suspend fun searchUserByName(@RequestBody body : AuthServiceInput.SearchUserInput,
                                 @RequestHeader headers: Map<String, String>
    ): STResponse<AuthServiceOutput.SearchUserStatus> {
        return getUserRequestChain<AuthServiceInput.SearchUserInput, AuthServiceOutput.SearchUserStatus>(userService,null, headers)
            .addItem { input, _ ->
                STResponse(userService.getUserByName(body), null)
            }.next()
    }

    @GetMapping("/user/getUserProps")
    suspend fun getUserProps(@RequestHeader headers: Map<String, String>) : STResponse<AuthServiceOutput.GetUserPropsStatus>{
        return getUserRequestChain<Any,AuthServiceOutput.GetUserPropsStatus>(userService,Unit,headers)
            .addItem { input, _ ->
                STResponse(userService.getUserProps(input[EXTRA_USER]), null)
            } .next()
    }

}