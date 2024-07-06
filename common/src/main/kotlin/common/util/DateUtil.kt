package com.sushobh.common.util

import common.util.Expirable
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Component
class DateUtil {

    fun getCurrentTime() = OffsetDateTime.now()



    fun hasExpired(expirable: Expirable) : Boolean{
        if(expirable.getUnit() == ChronoUnit.SECONDS){
            return expirable.getOriginTime().plusSeconds(expirable.getExpiryCount().seconds).isBefore(getCurrentTime())
        }
        if(expirable.getUnit() == ChronoUnit.HOURS){
            return expirable.getOriginTime().plusHours(expirable.getExpiryCount().toHours()).isBefore(getCurrentTime())
        }
        throw Exception("Not implemented")
    }

}