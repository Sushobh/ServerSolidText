package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "st_user")
internal class ETUser() : BaseTable() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
        private set

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