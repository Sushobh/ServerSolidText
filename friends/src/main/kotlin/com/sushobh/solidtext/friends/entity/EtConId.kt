package com.sushobh.solidtext.com.sushobh.solidtext.friends.entity

import java.math.BigInteger

class EtConId() {

    constructor(fromUserId: BigInteger, toUserid: BigInteger) : this() {
        this.fromUserId = fromUserId
        this.toUserid = toUserid
    }

    lateinit var fromUserId : BigInteger
    private set
    lateinit var toUserid : BigInteger
    private set

}