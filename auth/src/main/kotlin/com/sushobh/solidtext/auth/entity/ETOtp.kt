package com.sushobh.solidtext.auth.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.OffsetDateTime


@Entity
@Table(name = "otp")
class ETOtp() : BaseTable() {

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

}