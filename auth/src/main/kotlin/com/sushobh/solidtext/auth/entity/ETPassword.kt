package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "password")
internal class ETPassword() : BaseTable() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
        private set

    @Column(name = "password_text")
    lateinit var passwordText: String
        private set

    constructor(time: OffsetDateTime, passwordText: String) : this() {
        this.passwordText = passwordText
        this.time = time
    }
}