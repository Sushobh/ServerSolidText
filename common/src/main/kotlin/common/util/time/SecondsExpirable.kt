package common.util.time

import common.util.Expirable
import java.time.Duration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

class SecondsExpirable(val origTime : OffsetDateTime,val expiryCount : Long) : Expirable {
    override fun getOriginTime(): OffsetDateTime {
        return origTime
    }

    override fun getExpiryCount(): Duration {
        return Duration.ofSeconds(expiryCount)
    }

    override fun getUnit(): ChronoUnit {
        return ChronoUnit.SECONDS
    }
}