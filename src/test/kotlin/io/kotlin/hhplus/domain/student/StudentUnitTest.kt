package io.kotlin.hhplus.domain.student

import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import org.apache.coyote.BadRequestException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StudentUnitTest {

    @Nested
    inner class `신청 가능 검증` {
        @Test
        fun `이미 신청되어 lectureEnrollment에 포함되어 있으면 BadRequestException이 발생한다`() {
            // given
            val lectureEnrollment1 = createLectureEnrollment(1L)
            val lectureEnrollment2 = createLectureEnrollment(2L)
            val lectureEnrollment3 = createLectureEnrollment(3L)
            val lectureEnrollments = mutableListOf(lectureEnrollment1, lectureEnrollment2, lectureEnrollment3)

            val student = Instancio.of(Student::class.java)
                .set(field("lectureEnrollments"), lectureEnrollments)
                .create()

            // when then
            assertThatThrownBy { student.validateEnrollable(2L) }
                .isInstanceOf(BadRequestException::class.java)
                .hasMessage("이미 신청 완료된 특강입니다.")
        }
    }

    @Nested
    inner class `신청한 특강인지 여부` {
        @Test
        fun `이미 신청한 특강의 id를 넣으면 true를 반환한다`() {
            // given
            val lectureEnrollment1 = createLectureEnrollment(1L)
            val lectureEnrollment2 = createLectureEnrollment(2L)
            val lectureEnrollment3 = createLectureEnrollment(3L)
            val lectureEnrollments = mutableListOf(lectureEnrollment1, lectureEnrollment2, lectureEnrollment3)

            val student = Instancio.of(Student::class.java)
                .set(field("lectureEnrollments"), lectureEnrollments)
                .create()

            // when
            val result = student.isEnrolledLecture(3L)

            //then
            assertThat(result).isTrue()
        }

        @Test
        fun `신청한 적 없는 특강의 id를 넣으면 false를 반환한다`() {
            // given
            val lectureEnrollment1 = createLectureEnrollment(1L)
            val lectureEnrollment2 = createLectureEnrollment(2L)
            val lectureEnrollment3 = createLectureEnrollment(3L)
            val lectureEnrollments = mutableListOf(lectureEnrollment1, lectureEnrollment2, lectureEnrollment3)

            val student = Instancio.of(Student::class.java)
                .set(field("lectureEnrollments"), lectureEnrollments)
                .create()

            // when
            val result = student.isEnrolledLecture(5L)

            //then
            assertThat(result).isFalse()
        }
    }

    fun createLectureEnrollment(lectureId: Long): LectureEnrollment {
        return Instancio.of(LectureEnrollment::class.java)
            .set(field("lectureId"), lectureId)
            .create()
    }
}