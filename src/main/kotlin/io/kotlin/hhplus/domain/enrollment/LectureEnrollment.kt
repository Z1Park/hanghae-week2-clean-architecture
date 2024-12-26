package io.kotlin.hhplus.domain.enrollment

import io.kotlin.hhplus.domain.common.BaseAuditing
import jakarta.persistence.*

@Entity
class LectureEnrollment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false)
    var studentId: Long,

    @Column(nullable = false)
    var lectureId: Long
) : BaseAuditing() {

    companion object {
        fun of(studentId: Long, lectureId: Long): LectureEnrollment {
            return LectureEnrollment(null, studentId, lectureId)
        }
    }
}