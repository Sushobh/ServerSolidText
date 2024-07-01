package com.sushobh.solidtext.auth.service

import com.sushobh.solidtext.auth.entity.ETOtp
import com.sushobh.solidtext.auth.repository.OtpRepo
import com.sushobh.common.util.DateUtil
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.random.Random


@Component
class OtpSender(val dateUtil: DateUtil,val otpRepo: OtpRepo) {



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