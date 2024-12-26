package io.kotlin.hhplus.domain.lecture

import java.time.ZonedDateTime

interface LectureRepository {

    fun getAllOnDate(date: ZonedDateTime): List<Lecture>

    fun getByIdForUpdate(id: Long): Lecture

    fun getAll(lectureIds: List<Long>): List<Lecture>

    fun save(lecture: Lecture)
}