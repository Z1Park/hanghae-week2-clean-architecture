package io.kotlin.hhplus.domain.lecture

import java.time.ZonedDateTime

data class LectureInfo(
    val lectureId: Long,
    val lectureTitle: String,
    val instructorName: String,
    val capacity: Int,
    val studentCount: Int,
    val date: ZonedDateTime
) {
    companion object {
        fun from(lecture: Lecture): LectureInfo {
            return LectureInfo(
                lecture.id!!,
                lecture.title,
                lecture.instructorName,
                lecture.capacity,
                lecture.studentCount,
                lecture.date
            )
        }
    }
}