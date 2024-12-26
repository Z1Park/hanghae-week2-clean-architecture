package io.kotlin.hhplus.infrastructure.lecture

import io.kotlin.hhplus.domain.lecture.Lecture
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
interface LectureJpaRepository : JpaRepository<Lecture, Long> {

    fun findAllByDate(date: ZonedDateTime): List<Lecture>

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(
        """
        select l
        from Lecture l
        where l.id = :id
    """
    )
    fun findByIdForUpdate(@Param("id") id: Long): Lecture?

    fun findAllByIdIn(lectureIds: List<Long>): List<Lecture>
}