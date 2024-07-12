package com.sushobh.solidtext.posts.api

import com.sushobh.solidtext.auth.response.RespETUser
import java.time.OffsetDateTime

data class RespETPost(val text : String,val time : OffsetDateTime,val byUser : RespETUser)