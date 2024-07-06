package com.sushobh.solidtext.auth

import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.stereotype.Component
import java.util.function.Supplier


@Component
class MyAuthManager : AuthorizationManager<RequestAuthorizationContext> {
    override fun check(
        authentication: Supplier<Authentication>?,
        `object`: RequestAuthorizationContext?
    ): AuthorizationDecision {
        return AuthorizationDecision(true)
    }
}


