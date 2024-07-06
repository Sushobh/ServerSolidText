package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUserTokenPair
import com.sushobh.solidtext.auth.entity.ETUserTokenPairId
import org.springframework.data.repository.CrudRepository

interface ETUserTokenPairRepo : CrudRepository<ETUserTokenPair, ETUserTokenPairId> {

}