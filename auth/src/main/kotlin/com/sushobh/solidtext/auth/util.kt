package com.sushobh.solidtext.auth

import com.sushobh.solidtext.auth.service.UserService
import com.sushobh.solidtext.auth.service.UserTokenChecker
import common.util.requests.RequestChain
import common.util.requests.STRequest


fun <X, Y> getDefaultRequestChain(
    body: X?,
    headers: Map<String, String> = hashMapOf()
): RequestChain<X, Y> {
    return RequestChain.new(STRequest(headers, body))
}

fun <X, Y> getUserRequestChain(userService: UserService,body: X?, headers: Map<String, String> = hashMapOf()): RequestChain<X, Y> {
    return getDefaultRequestChain<X, Y>(body, headers)
        .addItem(UserTokenChecker(userService))
}