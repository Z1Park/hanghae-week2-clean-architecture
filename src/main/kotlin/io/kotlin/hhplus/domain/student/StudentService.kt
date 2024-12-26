package io.kotlin.hhplus.domain.student

import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    fun getWithLectureEnrollment(studentId: Long): Student {
        return studentRepository.getByIdWithLectureEnrollment(studentId)
    }
}