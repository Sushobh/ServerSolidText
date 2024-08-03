package com.sushobh.solidtext.com.sushobh.solidtext.friends.repos

import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETConReqRepo : CrudRepository<ETConnectionReq,BigInteger> {

    @Query("select *from connection_request where from_user = ?1 and to_user = ?2", nativeQuery = true)
    fun getExistingConnectionRequest(userId: BigInteger, toUserId: BigInteger): ETConnectionReq?
}