package com.sushobh.solidtext.posts.api

import com.sushobh.solidtext.auth.api.STUser
import com.sushobh.solidtext.auth.response.RespETUser
import com.sushobh.solidtext.posts.repos.PJPostFeedItem
import java.math.BigInteger
import java.time.Instant
import java.time.OffsetDateTime

data class RespETPost(val text : String,val time : OffsetDateTime,val byUser : RespETUser)


class STPost constructor(val id : BigInteger,
                         val byUser : STUser?,
                         val addedTime : Instant,
                         val postText : String,
                         val status : String) {

}