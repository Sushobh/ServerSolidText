package com.sushobh.solidtext

import com.sushobh.auth.controllers.LoginController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.repository.RepositoryDefinition
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity



@EntityScan(basePackages = ["com.sushobh.auth"])
@ComponentScan(basePackages = ["com.sushobh.auth","com.sushobh.common","com.sushobh.solidtext"])
@EnableJpaRepositories(basePackages = ["com.sushobh.auth"])
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class SolidTextApplication

fun main(args: Array<String>) {

	runApplication<SolidTextApplication>(*args)
}
