package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETOtp
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

internal interface OtpRepo : CrudRepository<ETOtp, BigInteger> {

}