package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETToken
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETTokenRepo : CrudRepository<ETToken, BigInteger> {
    fun findByTokenText(tokenText : String) : ETToken?
}