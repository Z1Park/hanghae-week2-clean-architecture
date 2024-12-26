package io.kotlin.hhplus.infrastructure.student

import io.kotlin.hhplus.domain.student.Student
import io.kotlin.hhplus.domain.student.StudentRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Repository

@Repository
class StudentRepositoryImpl(
    private val studentJpaRepository: StudentJpaRepository
) : StudentRepository {

    override fun getByIdWithLectureEnrollment(studentId: Long): Student {
        return studentJpaRepository.findByIdWithLectureEnrollments(studentId)
            ?: throw BadRequestException("Student를 찾을 수 없습니다. studentId=${studentId}")
    }
}