package com.sushobh.common.util

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class DateUtil {

    fun getCurrentTime() = OffsetDateTime.now()
}