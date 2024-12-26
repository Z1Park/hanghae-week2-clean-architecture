package io.kotlin.hhplus.infrastructure.enrollment

import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import io.kotlin.hhplus.domain.enrollment.LectureEnrollmentRepository
import org.springframework.stereotype.Service

@Service
class LectureEnrollmentRepositoryImpl(
    private val lectureEnrollmentJpaRepository: LectureEnrollmentJpaRepository
) : LectureEnrollmentRepository {

    override fun save(lectureEnrollment: LectureEnrollment) {
        lectureEnrollmentJpaRepository.save(lectureEnrollment)
    }
}