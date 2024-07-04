package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUser
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETUserRepo : CrudRepository<ETUser,BigInteger> {
    fun findByEmail(email : String) : ETUser?
}