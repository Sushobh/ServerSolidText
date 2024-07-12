package com.sushobh.solidtext.auth.repository

import com.sushobh.solidtext.auth.entity.ETOtp
import com.sushobh.solidtext.auth.entity.ETUser
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.math.BigInteger

internal interface OtpRepo : CrudRepository<ETOtp, BigInteger> {


    @Query(
        "select *from otp where id = ?1 limit 1",
        nativeQuery = true
    )
    fun findByOtpId(otpId: BigInteger): ETOtp?
}