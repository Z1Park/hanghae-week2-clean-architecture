package io.kotlin.hhplus.domain.lecture

import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import org.apache.coyote.BadRequestException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.instancio.Instancio
import org.instancio.Instancio.gen
import org.instancio.Select.field
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LectureUnitTest {

    @Nested
    inner class `특강 신청 가능 여부` {
        @Test
        fun `현재 학생 수가 정원보다 적으면 신청 가능한 특강이다`() {
            // given
            val availableLecture = Instancio.of(Lecture::class.java)
                .set(field("capacity"), 30)
                .generate(field("studentCount"), gen().ints().range(0, 29))
                .create()

            // when
            val result = availableLecture.isAvailableLecture()

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `현재 학생 수가 정원과 같거나 크면 신청 불가능한 특강이다`() {
            // given
            val fulledLecture = Instancio.of(Lecture::class.java)
                .set(field("capacity"), 30)
                .generate(field("studentCount"), gen().ints().range(30, 40))
                .create()

            // when
            val result = fulledLecture.isAvailableLecture()

            //then
            assertThat(result).isFalse()
        }
    }

    @Nested
    inner class `특강 신청` {
        @Test
        fun `특강 신청에 성공하면, 수강 인원이 1증가한다`() {
            // given
            val lecture = Instancio.of(Lecture::class.java)
                .set(field("id"), 11L)
                .set(field("capacity"), 30)
                .set(field("studentCount"), 0)
                .set(field("lectureEnrollments"), mutableListOf<LectureEnrollment>())
                .create()

            // when
            val result = lecture.enrollStudent(1L)

            // then
            assertThat(lecture.studentCount).isEqualTo(1)
            assertThat(lecture.lectureEnrollments).hasSize(1)

            assertThat(result.lectureId).isEqualTo(11L)
            assertThat(result.studentId).isEqualTo(1L)
        }

        @Test
        fun `남은 자리가 없다면 BadRequestException이 발생한다`() {
            // given
            val fulledLecture = Instancio.of(Lecture::class.java)
                .set(field("capacity"), 30)
                .set(field("studentCount"), 30)
                .create()
            val studentId = 1L

            // when then
            assertThatThrownBy { fulledLecture.enrollStudent(studentId) }
                .isInstanceOf(BadRequestException::class.java)
                .hasMessage("정원이 마감되어 신청에 실패했습니다.")
        }
    }
}
