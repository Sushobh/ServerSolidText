package com.sushobh.solidtext.posts.repos


import java.math.BigInteger
import java.time.Instant

internal interface PJPostFeedItem {
    val id : BigInteger
    val byUser : BigInteger
    val addedTime : Instant
    val postText : String
    val status : String
}