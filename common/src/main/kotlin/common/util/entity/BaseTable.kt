package com.sushobh.common.util.entity

import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@MappedSuperclass
open class BaseTable(){

    constructor(time : OffsetDateTime) : this(){
        this.time = time
    }




    @Column(name = "time")
    lateinit var time: OffsetDateTime
       protected set


}