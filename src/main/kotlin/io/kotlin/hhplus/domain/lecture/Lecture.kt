package io.kotlin.hhplus.domain.lecture

import io.kotlin.hhplus.domain.common.BaseAuditing
import io.kotlin.hhplus.domain.enrollment.LectureEnrollment
import jakarta.persistence.*
import org.apache.coyote.BadRequestException
import java.time.ZonedDateTime

@Entity
class Lecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var instructorName: String,

    @Column(nullable = false)
    var capacity: Int = 30,

    @Column(nullable = false)
    var studentCount: Int = 0,

    @Column
    var date: ZonedDateTime,

    @OneToMany(mappedBy = "lectureId", fetch = FetchType.LAZY)
    val lectureEnrollments: MutableList<LectureEnrollment> = mutableListOf()
) : BaseAuditing() {

    companion object {
        fun of(title: String, instructorName: String, date: ZonedDateTime): Lecture {
            return Lecture(null, title, instructorName, date = date)
        }
    }

    fun isAvailableLecture(): Boolean {
        return capacity > studentCount
    }

    fun enrollStudent(studentId: Long): LectureEnrollment {
        if (!isAvailableLecture()) {
            throw BadRequestException("정원이 마감되어 신청에 실패했습니다.")
        }

        val lectureEnrollment = LectureEnrollment.of(studentId, this.id!!)
        lectureEnrollments.add(lectureEnrollment)

        this.studentCount++
        return lectureEnrollment
    }
}