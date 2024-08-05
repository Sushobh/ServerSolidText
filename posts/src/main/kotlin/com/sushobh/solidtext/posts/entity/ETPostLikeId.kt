package com.sushobh.solidtext.posts.entity

import java.math.BigInteger



class ETPostLikeId() {

    constructor(userId: BigInteger, postId: BigInteger) : this() {
        this.userId = userId
        this.postId = postId
    }

    lateinit var userId : BigInteger
        private set
    lateinit var postId : BigInteger
        private set

}