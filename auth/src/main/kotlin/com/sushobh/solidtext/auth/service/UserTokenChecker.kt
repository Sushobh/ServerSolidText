package com.sushobh.solidtext.auth.service

import com.sushobh.solidtext.auth.AUTH_HEADER
import com.sushobh.solidtext.auth.EXTRA_USER
import com.sushobh.solidtext.auth.RESPONSE_STATUS_AUTH_INVALID
import com.sushobh.solidtext.auth.api.STUser
import common.util.requests.*
import org.springframework.stereotype.Component

@Component
internal class UserTokenChecker<X, Y>(val userService: UserService) : ChainItem<X, Y> {

    override suspend fun processRequest(input: STRequest<X>, chain: RequestChain<X, Y>): STResponse<Y> {
        val tokenText = input.getHeader(AUTH_HEADER.lowercase())
        tokenText?.let {
            val user = userService.getUserFromToken(tokenText)
            user?.let {
                input.addExtra(EXTRA_USER, STUser(userId = user.id))
                return chain.next()
            }
        }
        return STResponse(null, STErrorBody(status = RESPONSE_STATUS_AUTH_INVALID))
    }
}