package io.kotlin.hhplus.interfaces.student

import io.kotlin.hhplus.domain.lecture.LectureInfo
import java.time.ZonedDateTime

data class EnrolledLectureResponse(
    private val availableLectures: List<EnrolledLectureDto>
) {
    companion object {
        fun from(enrolledLectureCommands: List<LectureInfo>): EnrolledLectureResponse {
            val enrolledLectureDtos = enrolledLectureCommands.stream()
                .map { EnrolledLectureDto.from(it) }
                .toList()

            return EnrolledLectureResponse(enrolledLectureDtos)
        }
    }
}

data class EnrolledLectureDto(
    private val lectureId: Long,
    private val lectureTitle: String,
    private val instructorName: String,
    private val date: ZonedDateTime
) {
    companion object {
        fun from(enrolledLecture: LectureInfo): EnrolledLectureDto {
            return EnrolledLectureDto(
                enrolledLecture.lectureId,
                enrolledLecture.lectureTitle,
                enrolledLecture.instructorName,
                enrolledLecture.date
            )
        }
    }
}