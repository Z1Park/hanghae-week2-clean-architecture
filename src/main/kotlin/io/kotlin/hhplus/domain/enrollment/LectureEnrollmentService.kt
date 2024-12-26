package io.kotlin.hhplus.domain.enrollment

import org.springframework.stereotype.Service

@Service
class LectureEnrollmentService(
    private val lectureEnrollmentRepository: LectureEnrollmentRepository
) {

    fun save(lectureEnrollment: LectureEnrollment) {
        lectureEnrollmentRepository.save(lectureEnrollment)
    }
}