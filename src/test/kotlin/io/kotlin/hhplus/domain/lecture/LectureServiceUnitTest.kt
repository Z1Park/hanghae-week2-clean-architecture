package io.kotlin.hhplus.domain.lecture

import org.assertj.core.api.Assertions.assertThat
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.ZonedDateTime

@ExtendWith(MockitoExtension::class)
class LectureServiceUnitTest {

    @InjectMocks
    private lateinit var lectureService: LectureService

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @Test
    fun `오늘 있는 특강 중 신청자 수가 정원보다 작은 특강만 반환한다`() {
        // given
        val lectures = listOf(
            createLecture("lecture1", 30, 0),
            createLecture("lecture2", 30, 30),
            createLecture("lecture3", 30, 29),
            createLecture("lecture4", 30, 8)
        )
        val now = ZonedDateTime.now()

        `when`(lectureRepository.getAllOnDate(now)).then { lectures }

        // when
        val result = lectureService.getAvailableLecturesOnDate(now)

        //then
        assertThat(result).hasSize(3)
            .extracting("title")
            .containsExactly("lecture1", "lecture3", "lecture4")
    }

    fun createLecture(title: String, capacity: Int, studentCount: Int): Lecture {
        return Instancio.of(Lecture::class.java)
            .set(field("title"), title)
            .set(field("capacity"), capacity)
            .set(field("studentCount"), studentCount)
            .create()
    }
}