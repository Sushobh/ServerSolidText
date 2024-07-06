package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUser
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETUserRepo : CrudRepository<ETUser, BigInteger> {
    fun findByEmail(email: String): ETUser?

    @Query(
        "select *from st_user where id in (select userid from user_token_pair where tokenid = ?1) limit 1",
        nativeQuery = true
    )
    fun findUserByToken(tokenId: BigInteger): ETUser?
}