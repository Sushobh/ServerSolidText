package com.sushobh.solidtext.entity

import com.fasterxml.jackson.annotation.JsonProperty



const val BASE_URL = "http://10.0.2.2:8080"

data class AppConfig (
    @JsonProperty("SIGNUP_URL") val signupUrl : String = "$BASE_URL/public/signup",
    @JsonProperty("OTP_VALIDATE_URL") val otpValidateUrl : String = "$BASE_URL/public/otpValidate",
    @JsonProperty("LOGIN_URL") val loginUrl : String = "$BASE_URL/public/login",
    @JsonProperty("POST_TEXT_LENGTH") val postTextMaxLength : String = "200",
    @JsonProperty("CREATE_POST_URL") val createPostUrl : String = "$BASE_URL/posts/createPost"
)




