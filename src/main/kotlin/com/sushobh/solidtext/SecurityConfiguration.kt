package com.sushobh.solidtext

import com.sushobh.solidtext.auth.MyAuthManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain


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