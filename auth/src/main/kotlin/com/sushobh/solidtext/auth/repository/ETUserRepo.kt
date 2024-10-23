package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUser
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger

internal interface ETUserRepo : CrudRepository<ETUser, BigInteger> {
    fun findByEmail(email: String): ETUser?

    @Query(
        "select *from st_user where id in (select userid from user_token_pair where tokenid = ?1) limit 1",
        nativeQuery = true
    )
    fun findUserByToken(tokenId: BigInteger): ETUser?

    @Query(
        "select * from st_user where username = ?1 limit 1",
        nativeQuery = true
    )
    fun findUserByName(userName: String): ETUser?

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update st_user set username = ?1 where id = ?2", nativeQuery = true)
    fun updateUserName(newName : String,id : BigInteger)


    @Modifying
    @Transactional
    @Query(
        value = "UPDATE st_user " +
                "SET user_props = jsonb_set(COALESCE(user_props, '{}'),CONCAT('{', :key, '}')::text[] ,to_jsonb(:value) ) " +
                "WHERE id = :id",
        nativeQuery = true
    )
    fun updateUserProp(
        @Param("key") key : String,
        @Param("value")   value: String,
        @Param("id")   id: BigInteger
    )


}