package io.kotlin.hhplus.domain.lecture

import io.kotlin.hhplus.global.TimeHolder
import org.apache.coyote.BadRequestException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class LectureFacadeServiceValidatorUnitTest {

    private val lectureServiceValidator = LectureServiceValidator()

    @Test
    fun `특강 신청 가능 목록 조회 시 오늘 이후 날짜로 조회하면 오류가 발생하지 않는다`() {
        // given
        val testZoneDateTime = ZonedDateTime.of(
            2024, 12, 25,
            12, 25, 0, 0,
            ZoneId.of("Asia/Seoul")
        )
        val stubTimeHolder = TimeHolder { testZoneDateTime }

        val date = ZonedDateTime.of(
            2024, 12, 25,
            1, 59, 59, 0,
            ZoneId.of("Asia/Seoul")
        )

        // when then
        lectureServiceValidator.validateAvailableLectures(date, stubTimeHolder)
    }

    @Test
    fun `특강 신청 가능 목록 조회 시 오늘 이전의 날짜로 조회하면 BadRequestException이 발생한다`() {
        // given
        val testZoneDateTime = ZonedDateTime.of(
            2024, 12, 25,
            12, 25, 0, 0,
            ZoneId.of("Asia/Seoul")
        )
        val stubTimeHolder = TimeHolder { testZoneDateTime }

        val date = ZonedDateTime.of(
            2024, 12, 24,
            23, 59, 59, 59,
            ZoneId.of("Asia/Seoul")
        )

        // when then
        assertThatThrownBy { lectureServiceValidator.validateAvailableLectures(date, stubTimeHolder) }
            .isInstanceOf(BadRequestException::class.java)
            .hasMessage("과거의 날짜는 조회할 수 없습니다.")
    }
}