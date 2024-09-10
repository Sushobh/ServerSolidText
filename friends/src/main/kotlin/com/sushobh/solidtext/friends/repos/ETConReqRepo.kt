package com.sushobh.solidtext.com.sushobh.solidtext.friends.repos

import com.sushobh.solidtext.apiclasses.ISTFrenRequest
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
    fun updateStatusOfRequest(status : String, fromId : BigInteger, toId : BigInteger)


    @Query("SELECT cr.time   AS sentTime,\n" +
            "       cr.from_user AS senderId,\n" +
            "       cr.to_user   AS receiverId\n" +
            "FROM connection_request cr where cr.from_user = ?1 and cr.status = ?2", nativeQuery = true)
    fun getSentFrenRequestsByUserForStatus(from : BigInteger,status : String) : List<ISTFrenRequest>

    @Query("SELECT cr.time   AS sentTime,\n" +
            "       cr.from_user AS senderId,\n" +
            "       cr.to_user   AS receiverId\n" +
            "FROM connection_request cr where cr.to_user = ?1 and cr.status = ?2", nativeQuery = true)
    fun getReceiveRequestsByUserForStatus(to : BigInteger,status : String): List<ISTFrenRequest>

}