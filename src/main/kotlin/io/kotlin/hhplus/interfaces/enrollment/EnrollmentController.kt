package io.kotlin.hhplus.interfaces.enrollment

import io.kotlin.hhplus.application.enrollment.EnrollmentFacadeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/enrollments")
class EnrollmentController(
    private val enrollmentFacadeService: EnrollmentFacadeService
) {

    @PostMapping("")
    fun enrollLecture(
        @RequestBody request: EnrollmentRequest
    ) {
        enrollmentFacadeService.enrollLecture(request.studentId, request.lectureId)
    }
}