package com.sushobh.solidtext.auth

import com.sushobh.solidtext.auth.service.TokenService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.temporal.ChronoUnit

@Configuration
open class Configuration {


    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder(4)

    @Qualifier("loginTokenConfig")
    @Bean
    open fun loginTokenConfig() = TokenService.TokenConfig(TOKEN_USER_LOGIN, 1L, ChronoUnit.HOURS)

    @Qualifier("userPropKeys")
    @Bean
    open fun userPropKeys() = hashSetOf("profile_pic1","fullName")

}