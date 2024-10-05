package com.sushobh.solidtext.com.sushobh.solidtext.friends.repos

import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtConId
import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.EtFrenConnection
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETConRepo : CrudRepository<EtFrenConnection,EtConId> {


    @Query("(select *from fren_connection where from_user = ?1 and to_user = ?2) union " +
            "(select *from fren_connection where from_user = ?2 and to_user = ?1)", nativeQuery = true)
    fun getExistingConnections(from : BigInteger,to : BigInteger) : List<EtFrenConnection>

    @Query("(select * from fren_connection where to_user = ?1) union (select *from fren_connection where from_user = ?1)", nativeQuery = true)
    fun getFriendConnectionsForUser(userId : BigInteger) : List<EtFrenConnection>




}