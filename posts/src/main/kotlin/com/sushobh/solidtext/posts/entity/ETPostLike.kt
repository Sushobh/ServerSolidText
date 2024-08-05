package com.sushobh.solidtext.posts.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "post_like")
@IdClass(ETPostLikeId::class)
class ETPostLike() : BaseTable() {

    constructor(time : OffsetDateTime, userId: BigInteger, postId: BigInteger,) : this() {
        this.userId = userId
        this.postId = postId
        super.time = time
    }

    @Id
    @Column(name = "user_id")
    lateinit var userId: BigInteger
        private set
    @Id
    @Column(name = "post_id")
    lateinit var postId: BigInteger
        private set


}