package com.sushobh.solidtext.posts.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigInteger
import java.time.OffsetDateTime



@Entity
@Table(name = "post")
class ETPost() : BaseTable() {

    constructor(postText: String, status: String, time: OffsetDateTime,byUserId : BigInteger) : this() {
        this.postText = postText
        this.status = status
        this.time = time
        this.byUserId = byUserId
    }

    @Column(name = "by_user_id")
    lateinit var byUserId: BigInteger
        private set

    @Column(name = "post_text")
    lateinit var postText: String
        private set

    @Column(name = "status")
    lateinit var status: String
        private set

}