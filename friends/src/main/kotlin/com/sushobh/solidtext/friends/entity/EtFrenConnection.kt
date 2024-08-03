package com.sushobh.solidtext.com.sushobh.solidtext.friends.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "fren_connection")
@IdClass(EtConId::class)
class EtFrenConnection() : BaseTable() {


    constructor(time : OffsetDateTime, fromUserId: BigInteger, toUserid: BigInteger,) : this() {
        this.toUserid = toUserid
        this.fromUserId = fromUserId
        super.time = time
    }

    @Id
    @Column(name = "from_user")
    lateinit var fromUserId: BigInteger
        private set
    @Id
    @Column(name = "to_user")
    lateinit var toUserid: BigInteger
        private set


}