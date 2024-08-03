package com.sushobh.solidtext.com.sushobh.solidtext.friends.entity

import com.sushobh.common.util.entity.BaseTable
import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@Entity
@Table(name = "connection_request")
class ETConnectionReq() : BaseTable() {


    constructor(time: OffsetDateTime, status: String, from : BigInteger,to :BigInteger) : this()
    {
        super.time = time
        this.status = status
        this.fromUserId = from
        this.toUserid = to
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: BigInteger
        private set


    @Column(name = "status")
    lateinit var status: String
        private set

    @Column(name = "from_user")
    lateinit var fromUserId: BigInteger
        private set

    @Column(name = "to_user")
    lateinit var toUserid: BigInteger
        private set


}