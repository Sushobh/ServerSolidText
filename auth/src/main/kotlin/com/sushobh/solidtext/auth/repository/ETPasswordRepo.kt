package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETPassword
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETPasswordRepo : CrudRepository<ETPassword, BigInteger> {
}