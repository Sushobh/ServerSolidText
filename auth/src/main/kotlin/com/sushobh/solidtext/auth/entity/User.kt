package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "st_user")
internal class ETUser() : BaseTable() {

    @Column(name = "email")
    lateinit var email: String
        private set

    @Column(name = "username")
    lateinit var username: String
        private set

    @Column(name = "password_id")
    lateinit var passwordId: BigInteger
        private set

    constructor(time: OffsetDateTime, passwordId: BigInteger, username: String, email: String) : this() {
        this.time = time
        this.passwordId = passwordId
        this.username = username
        this.email = email
    }
}