package com.sushobh.solidtext.com.sushobh.solidtext.friends.repos

import com.sushobh.solidtext.apiclasses.STFrenRequest
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger

interface ETConReqRepo : CrudRepository<ETConnectionReq,BigInteger> {

    @Query("select *from active_fren_connection_requests where from_user = ?1 and to_user = ?2", nativeQuery = true)
    fun getExistingConnectionRequest(userId: BigInteger, toUserId: BigInteger): ETConnectionReq?

    @Transactional
    @Modifying
    @Query("update connection_request set status = ?1 where from_user = ?2 and to_user = ?3", nativeQuery = true)
    fun deActivateRequest(status : String, fromId : BigInteger, toId : BigInteger)

    @Query("select *from fren_requests_by_user where sender_id = ?1", nativeQuery = true)
    fun getSentRequests(from : BigInteger) : List<STFrenRequest>

    @Query("select *from fren_requests_by_user where receiver_id = ?1", nativeQuery = true)
    fun getReceivedRequests(to : BigInteger) : List<STFrenRequest>

}