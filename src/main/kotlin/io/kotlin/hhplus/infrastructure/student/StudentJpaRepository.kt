package io.kotlin.hhplus.infrastructure.student

import io.kotlin.hhplus.domain.student.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StudentJpaRepository : JpaRepository<Student, Long> {

    @Query(
        """
        select s
        from Student s
        left join fetch s.lectureEnrollments
        where s.id = :id
    """
    )
    fun findByIdWithLectureEnrollments(@Param("id") id: Long): Student?
}