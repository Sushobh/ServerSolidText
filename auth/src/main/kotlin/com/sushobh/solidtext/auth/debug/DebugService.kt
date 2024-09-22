package com.sushobh.solidtext.auth.debug

import com.sushobh.solidtext.auth.repository.OtpRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigInteger


@Service
class AuthDebugService() {
    @Autowired
    private lateinit var otpRepo: OtpRepo

    fun getOtpByStringId(id : String) : String? {
        return otpRepo.findByStringId(id)?.otp
    }
}