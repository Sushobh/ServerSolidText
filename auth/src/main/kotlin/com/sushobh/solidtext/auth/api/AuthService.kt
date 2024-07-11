package com.sushobh.solidtext.auth.api

import com.sushobh.solidtext.auth.getUserRequestChain
import com.sushobh.solidtext.auth.service.UserService
import org.springframework.stereotype.Service


@Service
class AuthService internal constructor(private val userService: UserService) {

    fun <X,Y> getAuthUserChain(headers: Map<String, String> = hashMapOf(),body : X) =
        getUserRequestChain<X,Y>(headers = headers,userService = userService, body = body)
}