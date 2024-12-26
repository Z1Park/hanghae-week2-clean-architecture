package io.kotlin.hhplus.application.enrollment

import io.kotlin.hhplus.MySQLTestContainer
import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import io.kotlin.hhplus.domain.lecture.Lecture
import io.kotlin.hhplus.domain.student.Student
import io.kotlin.hhplus.infrastructure.enrollment.LectureEnrollmentJpaRepository
import io.kotlin.hhplus.infrastructure.lecture.LectureJpaRepository
import io.kotlin.hhplus.infrastructure.student.StudentJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import java.time.ZonedDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EnrollmentConcurrencyIntegrationTest(
    @Autowired private val enrollmentFacadeService: EnrollmentFacadeService,
    @Autowired private val studentJpaRepository: StudentJpaRepository,
    @Autowired private val lectureEnrollmentJpaRepository: LectureEnrollmentJpaRepository,
    @Autowired private val lectureJpaRepository: LectureJpaRepository
) : MySQLTestContainer() {

    @Test
    fun `40명의 유저가 동일한 강의를 신청하면 선착순 30명만 성공한다`() {
        // given
        val repeatCount = 40
        val students = mutableListOf<Student>()
        for (i in 1L..repeatCount) {
            students.add(studentJpaRepository.save(createStudent(i)))
        }
        val lecture = createLecture(101L)
        lectureJpaRepository.save(lecture)

        val countDownLatch = CountDownLatch(repeatCount)
        val executorService = Executors.newFixedThreadPool(repeatCount)

        // when
        for (i in 1L..repeatCount) {
            executorService.execute() {
                try {
                    enrollmentFacadeService.enrollLecture(students[i.toInt()].id!!, lecture.id!!)
                } catch (e: Exception) {
                }
                countDownLatch.countDown()
            }
        }

        //then
        countDownLatch.await()

        val findLecture = lectureJpaRepository.findByIdOrNull(lecture.id)!!
        assertThat(findLecture.studentCount).isEqualTo(30)

        val lectureEnrollments = lectureEnrollmentJpaRepository.findAll().filter { it.lectureId == lecture.id }
        assertThat(lectureEnrollments).hasSize(30)
    }

    @Test
    fun `동일한 유저가 5번 신청 시 1번만 성공하고 나머지는 실패한다`() {
        // given
        val student = createStudent(201L)
        studentJpaRepository.save(student)

        val lecture = createLecture(202L)
        lectureJpaRepository.save(lecture)

        val countDownLatch = CountDownLatch(5)
        val executorService = Executors.newFixedThreadPool(5)

        // when
        for (i in 1..5) {
            executorService.execute() {
                try {
                    enrollmentFacadeService.enrollLecture(student.id!!, lecture.id!!)
                } catch (e: Exception) {
                }
                countDownLatch.countDown()
            }
        }

        //then
        countDownLatch.await()

        val findLecture = lectureJpaRepository.findByIdOrNull(lecture.id)!!
        assertThat(findLecture.studentCount).isEqualTo(1)

        val lectureEnrollments =
            lectureEnrollmentJpaRepository.findAll().filter { it.lectureId == lecture.id && it.studentId == student.id }
        assertThat(lectureEnrollments).hasSize(1)
    }

    fun createStudent(id: Long): Student {
        return Instancio.of(Student::class.java)
            .set(field("id"), id)
            .set(field("name"), "")
            .set(field("lectureEnrollments"), mutableListOf<LectureEnrollment>())
            .create()
    }

    fun createLecture(id: Long): Lecture {
        return Instancio.of(Lecture::class.java)
            .set(field("id"), id)
            .set(field("title"), "title")
            .set(field("instructorName"), "")
            .set(field("capacity"), 30)
            .set(field("studentCount"), 0)
            .set(field("date"), ZonedDateTime.now())
            .set(field("lectureEnrollments"), mutableListOf<LectureEnrollment>())
            .create()
    }
}