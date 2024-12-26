package io.kotlin.hhplus.interfaces.student

import io.kotlin.hhplus.application.student.StudentFacadeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentFacadeService: StudentFacadeService
) {

    @GetMapping("/{studentId}/lectures")
    fun getStudentEnrolledLecture(@PathVariable studentId: Long): EnrolledLectureResponse {
        val enrolledLectures = studentFacadeService.getEnrolledLectures(studentId)
        return EnrolledLectureResponse.from(enrolledLectures)
    }
}