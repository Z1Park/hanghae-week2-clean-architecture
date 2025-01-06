package io.kotlin.hhplus.application.lecture

import io.kotlin.hhplus.MySQLTestContainer
import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import io.kotlin.hhplus.domain.lecture.Lecture
import io.kotlin.hhplus.domain.student.Student
import io.kotlin.hhplus.global.TimeHolder
import io.kotlin.hhplus.infrastructure.enrollment.LectureEnrollmentJpaRepository
import io.kotlin.hhplus.infrastructure.lecture.LectureJpaRepository
import io.kotlin.hhplus.infrastructure.student.StudentJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import java.time.ZoneId
import java.time.ZonedDateTime

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LectureFacadeServiceIntegrationTest(
    @Autowired private val lectureFacadeService: LectureFacadeService,
    @Autowired private val studentJpaRepository: StudentJpaRepository,
    @Autowired private val lectureEnrollmentJpaRepository: LectureEnrollmentJpaRepository,
    @Autowired private val lectureJpaRepository: LectureJpaRepository
) : MySQLTestContainer() {

    @Test
    fun `25일의 신청 가능한 특강을 조회하면 24,25,26일의 강의 중 25일의 정원이 차지 않은 특강이 반환된다`() {
        // given
        val lecture1 = createLecture("lecture1", 0, createZoneDateTime(2024, 12, 26))
        val lecture2 = createLecture("lecture2", 30, createZoneDateTime(2024, 12, 25))
        val lecture3 = createLecture("lecture3", 19, createZoneDateTime(2024, 12, 24))
        val lecture4 = createLecture("lecture4", 7, createZoneDateTime(2024, 12, 25))
        val lecture5 = createLecture("lecture5", 30, createZoneDateTime(2024, 12, 24))

        val lectures = listOf(lecture1, lecture2, lecture3, lecture4, lecture5)
        lectureJpaRepository.saveAll(lectures)

        val student = Student.of("나학생")
        studentJpaRepository.save(student)

        val currentTIme = createZoneDateTime(2024, 12, 15)

        val date = createZoneDateTime(2024, 12, 25)

        // when
        val availableLectures =
            lectureFacadeService.getAvailableLectures(student.id!!, date, TimeHolder { currentTIme })

        //then
        assertThat(availableLectures).hasSize(1)
            .extracting("lectureTitle")
            .containsExactly("lecture4")
    }

    fun createLecture(title: String, studentCount: Int, date: ZonedDateTime): Lecture {
        return Instancio.of(Lecture::class.java)
            .set(field("title"), title)
            .set(field("instructorName"), "")
            .set(field("capacity"), 30)
            .set(field("studentCount"), studentCount)
            .set(field("date"), date)
            .set(field("lectureEnrollments"), mutableListOf<LectureEnrollment>())
            .create()

    }

    fun createZoneDateTime(year: Int, month: Int, day: Int): ZonedDateTime {
        return ZonedDateTime.of(
            year, month, day,
            12, 25, 10, 41,
            ZoneId.of("Asia/Seoul")
        )
    }
}