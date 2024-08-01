package com.sushobh.solidtext.com.sushobh.solidtext.friends.repos

import com.sushobh.solidtext.com.sushobh.solidtext.friends.entity.ETConnectionReq
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface ETConReqRepo : CrudRepository<ETConnectionReq,BigInteger> {

}