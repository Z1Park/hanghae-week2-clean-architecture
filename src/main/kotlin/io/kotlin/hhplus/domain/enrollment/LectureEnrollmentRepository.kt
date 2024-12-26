package io.kotlin.hhplus.domain.enrollment

interface LectureEnrollmentRepository {

    fun save(lectureEnrollment: LectureEnrollment)
}