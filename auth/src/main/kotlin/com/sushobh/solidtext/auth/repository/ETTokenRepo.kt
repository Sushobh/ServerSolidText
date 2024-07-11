package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETToken
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

internal interface ETTokenRepo : CrudRepository<ETToken, BigInteger> {
    fun findByTokenText(tokenText: String): ETToken?
}