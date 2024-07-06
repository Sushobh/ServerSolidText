package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.OffsetDateTime


@Entity
@Table(name = "token")
class ETToken() : BaseTable() {

    @Column(name = "token_text")
    lateinit var tokenText: String
        private set

    @Column(name = "token_type")
    lateinit var tokenType: String
        private set

    @Column(name = "expiry_unit")
    lateinit var expiryUnit: String
        private set

    @Column(name = "expires_in")
    var expiresIn: Long = 0
        private set

    constructor(
        tokenText: String,
        tokenType: String,
        expiryUnit: String,
        expiresIn: Long,
        time: OffsetDateTime
    ) : this() {
        this.tokenText = tokenText
        this.tokenType = tokenType
        this.expiryUnit = expiryUnit
        this.expiresIn = expiresIn
        this.time = time
    }

}
