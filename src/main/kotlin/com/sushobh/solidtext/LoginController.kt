package com.sushobh.solidtext

import com.sushobh.solidtext.entity.User
import com.sushobh.solidtext.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class LoginController(val userRepository: UserRepository) {
    data class LoginApiResponse(
            val token: String?,
            val isSuccessful: Boolean,
            val message: String,
            val code: String
    )

    data class LoginInput(val username : String, val password : String)

    data class SignupInput(val username : String,val phoneNumber : String,val password: String)

    @PostMapping("/login")
    suspend fun login(@RequestBody loginInput: LoginInput) : LoginApiResponse {
        return LoginApiResponse("asdkasjgo23487asogdhgaskudygasd",true,"Failed bro","SUCCESS")
    }

    @PostMapping("/signup")
    suspend fun signup(@RequestBody signupInput: SignupInput) : LoginApiResponse {
        return LoginApiResponse("asdkasjgo23487asogdhgaskudygasd",true,"Failed bro","SUCCESS")
    }

}