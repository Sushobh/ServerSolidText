package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUser
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger

interface ETUserRepo : CrudRepository<ETUser, BigInteger> {
    fun findByEmail(email: String): ETUser?

    @Query(
        "select *from st_user where id in (select userid from user_token_pair where tokenid = ?1) limit 1",
        nativeQuery = true
    )
    fun findUserByToken(tokenId: BigInteger): ETUser?

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update st_user set username = ?1 where id = ?2", nativeQuery = true)
    fun updateUserName(newName : String,id : BigInteger)
}