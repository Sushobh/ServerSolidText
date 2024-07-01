package com.sushobh.solidtext

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@EntityScan(basePackages = ["com.sushobh.solidtext.*"])
@ComponentScan(basePackages = ["com.sushobh.solidtext.*","com.sushobh.common"])
@EnableJpaRepositories(basePackages = ["com.sushobh.solidtext.*"])
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class SolidTextApplication

fun main(args: Array<String>) {

	runApplication<SolidTextApplication>(*args)
}
