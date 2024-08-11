package com.sushobh.solidtext.auth.api

import com.sushobh.solidtext.apiclasses.AuthServiceClasses

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
        val status =  userService.getUserByName(AuthServiceClasses.SearchUserInput(userName))
        when(status){
            AuthServiceClasses.SearchUserStatus.Failed -> return null
            is AuthServiceClasses.SearchUserStatus.Found -> return status.user
            AuthServiceClasses.SearchUserStatus.UserNotFound -> return null
        }
    }
}