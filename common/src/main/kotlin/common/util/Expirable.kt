package common.util

import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

interface Expirable {
    fun getOriginTime() : OffsetDateTime
    fun getExpiryCount() : Duration
    fun getUnit() : ChronoUnit
}