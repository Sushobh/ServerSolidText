package com.sushobh.auth.util

import org.springframework.stereotype.Component
import java.text.DecimalFormat
import java.util.*


@Component
class OtpGenerator {

    fun generateOtp() : String {
        return DecimalFormat("000000").format(Random().nextInt(999999))
    }

}