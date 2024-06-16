package com.sushobh.solidtext

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class SolidTextApplication

fun main(args: Array<String>) {
	runApplication<SolidTextApplication>(*args)
}
