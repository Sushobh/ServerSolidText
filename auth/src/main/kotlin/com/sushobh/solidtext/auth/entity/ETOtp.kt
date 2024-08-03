package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Cacheable(false)
@Entity
@Table(name = "otp")
internal class ETOtp() : BaseTable() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
        private set

    constructor(type: String, otp: String, time: OffsetDateTime) : this() {
        this.type = type
        this.otp = otp
        this.time = time
    }

    @Column(name = "request_type")
    lateinit var type: String
        private set

    @Column(name = "otp")
    lateinit var otp: String
        private set

    @Column(name = "stringid", insertable = false)
    var stringid: String = ""
        private set

}