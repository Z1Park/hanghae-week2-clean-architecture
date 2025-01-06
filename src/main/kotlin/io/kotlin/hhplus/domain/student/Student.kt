package io.kotlin.hhplus.domain.student

import io.kotlin.hhplus.domain.common.BaseAuditing
import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import jakarta.persistence.*
import org.apache.coyote.BadRequestException

@Entity
class Student(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "studentId", fetch = FetchType.LAZY)
    val lectureEnrollments: MutableList<LectureEnrollment> = mutableListOf()
) : BaseAuditing() {

    companion object {
        fun of(name: String): Student {
            return Student(null, name)
        }
    }

    fun validateEnrollable(lectureId: Long) {
        val enrollmentIds = lectureEnrollments.map { it.lectureId }.toSet()

        if (enrollmentIds.contains(lectureId)) {
            throw BadRequestException("이미 신청 완료된 특강입니다.")
        }
    }

    fun getEnrolledLectureIds(): List<Long> {
        return lectureEnrollments.map { it.lectureId }
    }

    fun isEnrolledLecture(lectureId: Long): Boolean {
        return lectureEnrollments.map { it.lectureId }.contains(lectureId)
    }
}