package io.kotlin.hhplus.interfaces.lecture

import io.kotlin.hhplus.application.lecture.LectureFacadeService
import io.kotlin.hhplus.global.TimeHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
@RequestMapping("/lectures")
class LectureController(
    private val lectureFacadeService: LectureFacadeService,
    private val timeHolder: TimeHolder
) {

    @GetMapping("/available")
    fun getAvailableLecture(
        @RequestParam("studentId") studentId: Long,
        @RequestParam("date") date: ZonedDateTime,
    ): AvailableLectureResponse {
        val availableLectures = lectureFacadeService.getAvailableLectures(studentId, date, timeHolder)

        return AvailableLectureResponse.from(availableLectures)
    }
}