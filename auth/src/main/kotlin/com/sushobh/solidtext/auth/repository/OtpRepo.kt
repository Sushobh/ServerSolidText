package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETOtp
import com.sushobh.solidtext.auth.entity.ETSignupAttempt
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

interface OtpRepo : CrudRepository<ETOtp,BigInteger> {

}