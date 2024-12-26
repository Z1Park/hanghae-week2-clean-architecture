package io.kotlin.hhplus.domain.student

interface StudentRepository {

    fun getByIdWithLectureEnrollment(studentId: Long): Student
}