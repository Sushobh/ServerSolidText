package com.sushobh.solidtext

import com.sushobh.auth.MyAuthManager
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.Authentication
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher


@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity,myAuthManager: MyAuthManager): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/public/**",permitAll)
                authorize("/user/**",authenticated)
                authorize("/**",myAuthManager)
            }
        }
        return http.build()
    }


}