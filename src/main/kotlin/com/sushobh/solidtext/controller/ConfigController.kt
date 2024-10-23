package com.sushobh.solidtext.controller

import com.sushobh.solidtext.entity.AppConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController(@Autowired
                       private val env : Environment) {


    @GetMapping("/config")
    suspend fun getConfig() : AppConfig{
        return AppConfig(env.getProperty("spring.BASE_URL") ?: "")
    }

}