package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETUserTokenPair
import com.sushobh.solidtext.auth.entity.ETUserTokenPairId
import org.springframework.data.repository.CrudRepository

internal interface ETUserTokenPairRepo : CrudRepository<ETUserTokenPair, ETUserTokenPairId> {

}