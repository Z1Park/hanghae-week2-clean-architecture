package io.kotlin.hhplus.global

import java.time.ZonedDateTime

fun interface TimeHolder {

    fun getCurrentTime(): ZonedDateTime
}