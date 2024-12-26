package io.kotlin.hhplus.interfaces.enrollment

data class EnrollmentRequest(
    val studentId: Long,
    val lectureId: Long
) {
}