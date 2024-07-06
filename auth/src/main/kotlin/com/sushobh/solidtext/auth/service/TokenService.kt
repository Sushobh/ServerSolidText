package com.sushobh.solidtext.auth.service

import com.sushobh.common.util.DateUtil
import com.sushobh.solidtext.auth.entity.ETToken
import com.sushobh.solidtext.auth.repository.ETTokenRepo
import common.util.time.HoursExpirable
import common.util.time.SecondsExpirable
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit
import java.util.UUID

@Component
class TokenService(private val tokenRepo : ETTokenRepo,private val dateUtil: DateUtil)  {

    class TokenConfig(val name : String,val expiresIn : Long,val expiryUnit : ChronoUnit)


    private fun generateRandomText() : String {
        val builder = StringBuilder()
        repeat(6, {
            builder.append(UUID.randomUUID())
        })
        return builder.toString()
    }

    fun generateToken(tokenConfig: TokenConfig) : ETToken {
        val etToken = ETToken(tokenText = generateRandomText(),
            tokenType = tokenConfig.name,
            expiresIn = tokenConfig.expiresIn,
            expiryUnit = tokenConfig.expiryUnit.name,
            time = dateUtil.getCurrentTime()
            )
        return tokenRepo.save(etToken)
    }

    fun validateToken(tokenText: String): ETToken {
        val exception = Exception("Invalid Token Text")
        val token = tokenRepo.findByTokenText(tokenText)
        token?.let {
            when(token.expiryUnit){
                ChronoUnit.HOURS.name -> {
                    if(dateUtil.hasExpired(token.toHoursExpirable())){
                        throw exception
                    }
                }
                ChronoUnit.SECONDS.name -> {
                    if(dateUtil.hasExpired(token.toSecondsExpirable())){
                        throw exception
                    }
                }
                else -> throw exception
            }
            return token
        }
        throw exception
    }

    private fun ETToken.toHoursExpirable() = HoursExpirable(time,expiresIn)
    private fun ETToken.toSecondsExpirable() = SecondsExpirable(time,expiresIn)
}