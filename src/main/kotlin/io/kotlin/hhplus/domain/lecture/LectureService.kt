package io.kotlin.hhplus.domain.lecture

import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class LectureService(
    private val lectureRepository: LectureRepository
) {

    fun getAvailableLecturesOnDate(date: ZonedDateTime): List<Lecture> {
        val lecturesOnDate: List<Lecture> = lectureRepository.getAllOnDate(date)

        return lecturesOnDate.filter { it.isAvailableLecture() }
    }

    fun getLectureForUpdate(lectureId: Long): Lecture {
        return lectureRepository.getByIdForUpdate(lectureId)
    }

    fun getLectures(lectureIds: List<Long>): List<Lecture> {
        return lectureRepository.getAll(lectureIds)
    }

    fun save(lecture: Lecture) {
        lectureRepository.save(lecture)
    }
}