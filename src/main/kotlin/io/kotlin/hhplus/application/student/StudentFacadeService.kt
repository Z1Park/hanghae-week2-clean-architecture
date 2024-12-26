package io.kotlin.hhplus.application.student

import io.kotlin.hhplus.domain.lecture.LectureInfo
import io.kotlin.hhplus.domain.lecture.LectureService
import io.kotlin.hhplus.domain.student.StudentService
import org.springframework.stereotype.Service

@Service
class StudentFacadeService(
    private val lectureService: LectureService,
    private val studentService: StudentService
) {

    fun getEnrolledLectures(studentId: Long): List<LectureInfo> {
        val student = studentService.getWithLectureEnrollment(studentId)
        println(student.name)
        println(student.lectureEnrollments)

        val lectures = lectureService.getLectures(student.getEnrolledLectureIds())
        return lectures.map { LectureInfo.from(it) }
    }
}