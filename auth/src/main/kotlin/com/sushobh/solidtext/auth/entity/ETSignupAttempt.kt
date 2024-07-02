package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "signup_attempt")
class ETSignupAttempt() : BaseTable() {

    @Column(name = "email")
    private lateinit var email: String

    constructor(email: String, password: String, time: OffsetDateTime, otp_id: BigInteger? = null) : this() {
        this.time = time
        this.email = email
        this.password = password
        this.otpId = otp_id
    }

    @Column(name = "pwd")
    lateinit var password: String
        private set

    @Column(name = "otp_id")
    var otpId: BigInteger? = null
        private set

}