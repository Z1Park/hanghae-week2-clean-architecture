package io.kotlin.hhplus.global

import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class SystemTimeHolder: TimeHolder {

    override fun getCurrentTime(): ZonedDateTime {
        return ZonedDateTime.now()
    }
}