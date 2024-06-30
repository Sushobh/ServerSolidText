package com.sushobh.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "signup_attempt")
class SignupAttempt() : BaseTable(){

    @Column(name = "email")
    private lateinit var email : String

    constructor(email: String, password: String,time : OffsetDateTime) : this() {
        this.time = time
        this.email = email
        this.password = password
    }

    @Column(name = "pwd")
    protected lateinit var password : String

}