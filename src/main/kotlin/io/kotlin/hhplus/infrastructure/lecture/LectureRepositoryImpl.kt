package io.kotlin.hhplus.infrastructure.lecture

import io.kotlin.hhplus.domain.lecture.Lecture
import io.kotlin.hhplus.domain.lecture.LectureRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
) : LectureRepository {

    override fun getAllOnDate(date: ZonedDateTime): List<Lecture> {
        return lectureJpaRepository.findAllByDate(date);
    }

    override fun getByIdForUpdate(id: Long): Lecture {
        return lectureJpaRepository.findByIdForUpdate(id)
            ?: throw BadRequestException("Lecture를 찾을 수 없습니다. lectureId=${id}")
    }

    override fun getAll(lectureIds: List<Long>): List<Lecture> {
        return lectureJpaRepository.findAllByIdIn(lectureIds)
    }

    override fun save(lecture: Lecture) {
        lectureJpaRepository.save(lecture)
    }
}