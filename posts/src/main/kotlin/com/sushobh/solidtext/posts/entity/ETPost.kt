package com.sushobh.solidtext.posts.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime



@Entity
@Table(name = "post")
class ETPost() : BaseTable() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
        private set

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