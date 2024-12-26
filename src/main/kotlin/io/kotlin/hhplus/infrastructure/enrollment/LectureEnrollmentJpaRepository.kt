package io.kotlin.hhplus.infrastructure.enrollment

import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureEnrollmentJpaRepository : JpaRepository<LectureEnrollment, Long> {

    fun existsByStudentIdAndLectureId(studentId: Long, lectureId: Long): Boolean
}