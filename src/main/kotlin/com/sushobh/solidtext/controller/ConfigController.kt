package com.sushobh.solidtext.controller

import com.sushobh.solidtext.entity.AppConfig
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController {


    @GetMapping("/config")
    suspend fun getConfig() : AppConfig{
        return AppConfig()
    }

}