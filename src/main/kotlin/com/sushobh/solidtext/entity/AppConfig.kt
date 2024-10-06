package com.sushobh.solidtext.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.net.DatagramSocket
import java.net.InetAddress


val BASE_URL : String by lazy {
   DatagramSocket().use { socket ->
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002)
        "http://"+socket.localAddress.hostAddress+":8080"
    }
}

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
    @JsonProperty("URL_GET_USER_POSTS") val post : String = "$BASE_URL/posts/getUserPosts",
    @JsonProperty("URL_GET_OTHER_USER_POSTS") val otherUserPosts : String = "$BASE_URL/posts/getOtherUserPosts",
    @JsonProperty("URL_SENT_FREN_REQ_LIST") val sentFrenReqs  : String = "$BASE_URL/frens/sentFriendRequests",
    @JsonProperty("URL_RECEIVED_FREN_REQ_LIST") val receivedFrenReqs  : String = "$BASE_URL/frens/receivedRequests",
    @JsonProperty("URL_FREN_REQ_ACTION") val cancelFrenReq  : String = "$BASE_URL/frens/reqAction",
    @JsonProperty("URL_SEND_FREN_REQ") val sendFrenReq : String = "$BASE_URL/frens/sendFrenReq",
    @JsonProperty("URL_FRENLIST") val friendList : String = "$BASE_URL/frens/getFriends",
    @JsonProperty("URL_GET_OTHER_USER_INFO") val otherUserInfo : String = "$BASE_URL/user/otheruserInfo"
)




