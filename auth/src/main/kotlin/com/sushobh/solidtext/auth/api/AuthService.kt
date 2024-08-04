package com.sushobh.solidtext.auth.api

import com.sushobh.solidtext.auth.getUserRequestChain
import com.sushobh.solidtext.auth.service.UserService
import org.springframework.stereotype.Service
import java.math.BigInteger


@Service
class AuthService internal constructor(private val userService: UserService) {

    fun <X,Y> getAuthUserChain(headers: Map<String, String> = hashMapOf(),body : X) =
        getUserRequestChain<X,Y>(headers = headers,userService = userService, body = body)



    suspend fun getUserByUserName(userName : String) : STUser? {
        val status =  userService.getUserByName(UserService.SearchUserInput(userName))
        when(status){
            UserService.SearchUserStatus.Failed -> return null
            is UserService.SearchUserStatus.Found -> return status.user
            UserService.SearchUserStatus.UserNotFound -> return null
        }
    }
}