package com.sushobh.solidtext.auth.service

import com.sushobh.solidtext.auth.entity.ETOtp
import com.sushobh.solidtext.auth.repository.OtpRepo
import com.sushobh.common.util.DateUtil
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.util.*
import kotlin.random.Random


@Component
class OtpService(val dateUtil: DateUtil, val otpRepo: OtpRepo) {


    fun getOtp(id : BigInteger) : ETOtp? {
        return otpRepo.findById(id).orElse(null)
    }

    fun sendOtp(type : String) : ETOtp {
        val otpText = generateOtpString()
        var etOtp = ETOtp(type,otpText,dateUtil.getCurrentTime())
        etOtp = otpRepo.save(etOtp)
        return etOtp
    }


    private fun generateOtpString() : String {
        val otp = StringBuilder()
        repeat(6) {
            otp.append(Random.nextInt(0, 10))
        }
        return otp.toString()
    }

}