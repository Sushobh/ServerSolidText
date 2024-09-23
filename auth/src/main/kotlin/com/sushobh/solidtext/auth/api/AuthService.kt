package com.sushobh.solidtext.auth.api

import com.sushobh.solidtext.apiclasses.AuthServiceOutput
import com.sushobh.solidtext.apiclasses.AuthServiceInput

import com.sushobh.solidtext.apiclasses.STUser
import com.sushobh.solidtext.auth.getUserRequestChain
import com.sushobh.solidtext.auth.service.UserService
import org.springframework.stereotype.Service
import java.math.BigInteger


@Service
class AuthService internal constructor(private val userService: UserService) {

    fun <X,Y> getAuthUserChain(headers: Map<String, String> = hashMapOf(),body : X) =
        getUserRequestChain<X,Y>(headers = headers,userService = userService, body = body)


    suspend fun getUserByid(id : BigInteger) : STUser? {
        return userService.getUserById(id)
    }

    suspend fun getUserByUserName(userName : String) : STUser? {
        val status =  userService.getUserByName(AuthServiceInput.SearchUserInput(userName))
        when(status){
            is AuthServiceOutput.SearchUserStatus.Failed -> return null
            is AuthServiceOutput.SearchUserStatus.Found -> return status.user
            is AuthServiceOutput.SearchUserStatus.UserNotFound -> return null
        }
    }
}