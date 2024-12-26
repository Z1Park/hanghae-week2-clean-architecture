package io.kotlin.hhplus.domain.lecture

import io.kotlin.hhplus.global.TimeHolder
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class LectureServiceValidator {

    fun validateAvailableLectures(dateTime: ZonedDateTime, currentDateTime: TimeHolder) {
        val date = dateTime.toLocalDate()
        val currentDate = currentDateTime.getCurrentTime().toLocalDate()

        if (date.isBefore(currentDate)) {
            throw BadRequestException("과거의 날짜는 조회할 수 없습니다.")
        }
    }
}