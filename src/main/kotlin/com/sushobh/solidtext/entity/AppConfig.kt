package com.sushobh.solidtext.entity

import com.fasterxml.jackson.annotation.JsonProperty



const val BASE_URL = "http://10.0.2.2:8080"

data class AppConfig (
    @JsonProperty("SIGNUP_URL") val signupUrl : String = "$BASE_URL/public/signup",
    @JsonProperty("OTP_VALIDATE_URL") val otpValidateUrl : String = "$BASE_URL/public/otpValidate",
    @JsonProperty("LOGIN_URL") val loginUrl : String = "$BASE_URL/public/login",
    @JsonProperty("POST_TEXT_LENGTH") val postTextMaxLength : String = "200",
    @JsonProperty("POST_TEXT_MIN_LENGTH") val postTextMinLength : String = "50",
    @JsonProperty("CREATE_POST_URL") val createPostUrl : String = "$BASE_URL/posts/createPost",
    @JsonProperty("URL_SEARCH_BY_USERNAME") val urlSearchByUsername : String = "$BASE_URL/frens/searchByUserName",
    @JsonProperty("URL_UPDATE_USER_KEY") val updateUserKey : String = "$BASE_URL/user/updateProperty",
    @JsonProperty("URL_GET_USER_PROPS") val getUserProps : String = "$BASE_URL/user/getUserProps",
    @JsonProperty("URL_GET_USER_POSTS") val post : String = "$BASE_URL/posts/getUserPosts"
)




