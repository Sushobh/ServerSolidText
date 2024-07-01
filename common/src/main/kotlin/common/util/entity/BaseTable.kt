package com.sushobh.common.util.entity

import jakarta.persistence.*
import java.math.BigInteger
import java.time.OffsetDateTime


@MappedSuperclass
open class BaseTable(){

    constructor(time : OffsetDateTime) : this(){
        this.time = time
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "signup_attempt_id_seq")
    @SequenceGenerator(name = "signup_attempt_id_seq", sequenceName = "signup_attempt_id_seq", allocationSize = 1 )
    lateinit var id : BigInteger
       private set

    @Column(name = "time")
    lateinit var time: OffsetDateTime
       protected set


}