package io.kotlin.hhplus.interfaces.lecture

import io.kotlin.hhplus.domain.lecture.LectureInfo
import java.time.ZonedDateTime

data class AvailableLectureResponse(
    val availableLectures: List<AvailableLectureDto>
) {

    companion object {
        fun from(availableLectures: List<LectureInfo>): AvailableLectureResponse {
            val availableLecturesDtos = availableLectures.stream()
                .map { AvailableLectureDto.from(it) }
                .toList()

            return AvailableLectureResponse(availableLecturesDtos)
        }
    }
}

data class AvailableLectureDto(
    val lectureScheduleId: Long,
    val lectureTitle: String,
    val instructorName: String,
    val date: ZonedDateTime,
    val totalStudentCount: Int,
    val remainStudentCount: Int
) {
    companion object {
        fun from(availableLecture: LectureInfo): AvailableLectureDto {
            return AvailableLectureDto(
                availableLecture.lectureId,
                availableLecture.lectureTitle,
                availableLecture.instructorName,
                availableLecture.date,
                availableLecture.capacity,
                availableLecture.studentCount
            )
        }
    }
}