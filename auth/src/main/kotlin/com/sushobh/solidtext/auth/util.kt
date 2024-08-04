package com.sushobh.solidtext.auth

import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.auth.entity.ETUser
import com.sushobh.solidtext.auth.service.UserService
import com.sushobh.solidtext.auth.service.UserTokenChecker
import common.util.requests.RequestChain
import common.util.requests.STRequest


internal fun <X, Y> getDefaultRequestChain(
    body: X?,
    headers: Map<String, String> = hashMapOf()
): RequestChain<X, Y> {
    return RequestChain.new(STRequest(headers, body))
}

internal fun <X, Y> getUserRequestChain(userService: UserService,body: X?, headers: Map<String, String> = hashMapOf()): RequestChain<X, Y> {
    return getDefaultRequestChain<X, Y>(body, headers)
        .addItem(UserTokenChecker(userService))
}


internal fun ETUser.toStUser() : STUser = STUser(id,username)