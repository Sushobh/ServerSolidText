package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.LOGIN_TOKEN_EXPIRY_IN_HOURS
import com.sushobh.solidtext.auth.TOKEN_USER_LOGIN
import com.sushobh.solidtext.auth.entity.ETToken
import com.sushobh.solidtext.auth.repository.ETTokenRepo
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit
import java.util.UUID

@Component
class TokenService(val tokenRepo : ETTokenRepo,val dateUtil: DateUtil)  {

    private fun generateRandomText() : String {
        val builder = StringBuilder()
        repeat(6, {
            builder.append(UUID.randomUUID())
        })
        return builder.toString()
    }

    fun generateLoginToken() : ETToken {
        val etToken = ETToken(tokenText = generateRandomText(),
            tokenType = TOKEN_USER_LOGIN,
            expiresIn = LOGIN_TOKEN_EXPIRY_IN_HOURS ,
            expiryUnit = ChronoUnit.HOURS.name,
            time = dateUtil.getCurrentTime()
            )
        return tokenRepo.save(etToken)
    }

}