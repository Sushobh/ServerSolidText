package com.sushobh.auth.entity

import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "signup_attempt")
class SignupAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger

    @Column(name = "time")
    lateinit var time: OffsetDateTime

    @Column(name = "email")
    lateinit var email : String

    @Column(name = "pwd")
    lateinit var password : String

}