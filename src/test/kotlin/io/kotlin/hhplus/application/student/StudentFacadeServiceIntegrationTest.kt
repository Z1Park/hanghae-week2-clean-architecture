package io.kotlin.hhplus.application.student

import io.kotlin.hhplus.MySQLTestContainer
import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import io.kotlin.hhplus.domain.lecture.Lecture
import io.kotlin.hhplus.domain.student.Student
import io.kotlin.hhplus.infrastructure.enrollment.LectureEnrollmentJpaRepository
import io.kotlin.hhplus.infrastructure.lecture.LectureJpaRepository
import io.kotlin.hhplus.infrastructure.student.StudentJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import java.time.ZoneId
import java.time.ZonedDateTime

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentFacadeServiceIntegrationTest(
    @Autowired private val studentFacadeService: StudentFacadeService,
    @Autowired private val studentJpaRepository: StudentJpaRepository,
    @Autowired private val lectureEnrollmentJpaRepository: LectureEnrollmentJpaRepository,
    @Autowired private val lectureJpaRepository: LectureJpaRepository
) : MySQLTestContainer() {

    @Test
    fun `2개의 강의를 신청한 학생의 신청 목록을 조회하면 2개의 강의 정보가 반환된다`() {
        // given
        val lecture1 = Lecture.of("강의1", "나강사", createZoneDateTime(2024, 12, 25))
        val lecture2 = Lecture.of("강의2", "나강사", createZoneDateTime(2024, 12, 25))
        lectureJpaRepository.save(lecture1)
        lectureJpaRepository.save(lecture2)

        val student = Student.of("나학생")
        studentJpaRepository.save(student)

        val lectureEnrollment1 = LectureEnrollment.of(student.id!!, lecture1.id!!)
        val lectureEnrollment2 = LectureEnrollment.of(student.id!!, lecture2.id!!)
        lectureEnrollmentJpaRepository.save(lectureEnrollment1)
        lectureEnrollmentJpaRepository.save(lectureEnrollment2)

        // when
        val enrolledLectures = studentFacadeService.getEnrolledLectures(student.id!!)

        //then
        assertThat(enrolledLectures).hasSize(2)
            .extracting("lectureTitle")
            .containsExactly("강의1", "강의2")
    }

    fun createZoneDateTime(year: Int, month: Int, day: Int): ZonedDateTime {
        return ZonedDateTime.of(
            year, month, day,
            0, 0, 0, 0,
            ZoneId.of("Asia/Seoul")
        )
    }
}