package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.entity.ETOtp
import com.sushobh.solidtext.auth.repository.OtpRepo
import org.springframework.stereotype.Component
import java.math.BigInteger
import kotlin.random.Random


@Component
class OtpService internal constructor(val dateUtil: DateUtil,private val otpRepo: OtpRepo) {


     internal fun getOtp(id: BigInteger): ETOtp? {
        return otpRepo.findById(id).orElse(null)
    }

    internal fun sendOtp(type: String): ETOtp {
        val otpText = generateOtpString()
        var etOtp = ETOtp(type, otpText, dateUtil.getCurrentTime())
        etOtp = otpRepo.save(etOtp)
        return etOtp
    }


    private fun generateOtpString(): String {
        val otp = StringBuilder()
        repeat(6) {
            otp.append(Random.nextInt(0, 10))
        }
        return otp.toString()
    }

}