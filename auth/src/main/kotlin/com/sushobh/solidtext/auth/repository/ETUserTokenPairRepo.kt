package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETSignupAttempt
import com.sushobh.solidtext.auth.entity.ETUser
import com.sushobh.solidtext.auth.entity.ETUserTokenPair
import com.sushobh.solidtext.auth.entity.ETUserTokenPairId
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETUserTokenPairRepo : CrudRepository<ETUserTokenPair,ETUserTokenPairId> {

}