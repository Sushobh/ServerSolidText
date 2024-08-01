package com.sushobh.solidtext.com.sushobh.solidtext.friends

import com.sushobh.common.util.DateUtil
import jakarta.persistence.Column
import org.springframework.stereotype.Service
import java.math.BigInteger


@Service
class FriendsService(private val dateUtil: DateUtil) {

    @Column(name = "from_userid")
    lateinit var fromUserId: BigInteger
        private set

    @Column(name = "to_userid")
    lateinit var toUserId: BigInteger
        private set


    @Column(name = "status")
    lateinit var status: String
        private set

}