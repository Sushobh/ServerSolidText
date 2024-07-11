package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.OffsetDateTime


@Entity
@Table(name = "password")
internal class ETPassword() : BaseTable() {

    @Column(name = "password_text")
    lateinit var passwordText: String
        private set

    constructor(time: OffsetDateTime, passwordText: String) : this() {
        this.passwordText = passwordText
        this.time = time
    }
}