package io.kotlin.hhplus.application.enrollment

import io.kotlin.hhplus.domain.enrollment.LectureEnrollmentService
import io.kotlin.hhplus.domain.lecture.LectureService
import io.kotlin.hhplus.domain.student.StudentService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class EnrollmentFacadeService(
    private val studentService: StudentService,
    private val lectureService: LectureService,
    private val lectureEnrollmentService: LectureEnrollmentService
) {

    @Transactional
    fun enrollLecture(studentId: Long, lectureId: Long): Long {
        val lecture = lectureService.getLectureForUpdate(lectureId)

        val student = studentService.getWithLectureEnrollment(studentId)
        student.validateEnrollable(lectureId)

        val lectureEnrollment = lecture.enrollStudent(student.id!!)

        lectureService.save(lecture)
        lectureEnrollmentService.save(lectureEnrollment)

        return lectureEnrollment.id!!
    }
}