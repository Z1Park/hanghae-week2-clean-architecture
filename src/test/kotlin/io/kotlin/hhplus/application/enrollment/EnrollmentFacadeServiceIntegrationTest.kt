package io.kotlin.hhplus.application.enrollment

import io.kotlin.hhplus.MySQLTestContainer
import io.kotlin.hhplus.domain.lecture.Lecture
import io.kotlin.hhplus.domain.student.Student
import io.kotlin.hhplus.infrastructure.enrollment.LectureEnrollmentJpaRepository
import io.kotlin.hhplus.infrastructure.lecture.LectureJpaRepository
import io.kotlin.hhplus.infrastructure.student.StudentJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import java.time.ZoneId
import java.time.ZonedDateTime

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EnrollmentFacadeServiceIntegrationTest(
    @Autowired private val enrollmentFacadeService: EnrollmentFacadeService,
    @Autowired private val studentJpaRepository: StudentJpaRepository,
    @Autowired private val lectureEnrollmentJpaRepository: LectureEnrollmentJpaRepository,
    @Autowired private val lectureJpaRepository: LectureJpaRepository
) : MySQLTestContainer() {

    @Test
    fun `studentId와 lectureId로 LectureEnrollment를 저장하고 lecture의 studentCount를 증가시킨다`() {
        // given
        val lecture = Lecture.of("강의", "나강사", createZoneDateTime(2024, 12, 25))
        lectureJpaRepository.save(lecture)

        val student = Student.of("나학생")
        studentJpaRepository.save(student)

        // when
        val savedEnrollmentId = enrollmentFacadeService.enrollLecture(student.id!!, lecture.id!!)

        //then
        val findLecture = lectureJpaRepository.findByIdOrNull(lecture.id!!)!!
        assertThat(findLecture.studentCount).isEqualTo(1)

        val findEnrollment = lectureEnrollmentJpaRepository.findByIdOrNull(savedEnrollmentId)!!
        assertThat(findEnrollment.studentId).isEqualTo(student.id)
        assertThat(findEnrollment.lectureId).isEqualTo(lecture.id)
    }

    fun createZoneDateTime(year: Int, month: Int, day: Int): ZonedDateTime {
        return ZonedDateTime.of(
            year, month, day,
            0, 0, 0, 0,
            ZoneId.of("Asia/Seoul")
        )
    }
}