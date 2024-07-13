package com.sushobh.solidtext.entity

import com.fasterxml.jackson.annotation.JsonProperty



const val BASE_URL = "http://10.0.2.2:8080"

data class AppConfig (
    @JsonProperty("SIGNUP_URL") val loginUrl : String = "$BASE_URL/public/signup",
    @JsonProperty("OTP_VALIDATE_URL") val otpValidateUrl : String = "$BASE_URL/public/otpValidate"
)




