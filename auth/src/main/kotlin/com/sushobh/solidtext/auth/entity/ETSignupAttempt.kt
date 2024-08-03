package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "signup_attempt")
internal class ETSignupAttempt() : BaseTable() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
        private set

    @Column(name = "email")
    lateinit var email: String
        private set

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