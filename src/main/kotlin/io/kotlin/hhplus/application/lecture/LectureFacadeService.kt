package io.kotlin.hhplus.application.lecture

import io.kotlin.hhplus.domain.lecture.LectureInfo
import io.kotlin.hhplus.domain.lecture.LectureService
import io.kotlin.hhplus.domain.lecture.LectureServiceValidator
import io.kotlin.hhplus.domain.student.StudentService
import io.kotlin.hhplus.global.TimeHolder
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class LectureFacadeService(
    private val lectureServiceValidator: LectureServiceValidator,
    private val lectureService: LectureService,
    private val studentService: StudentService
) {

    fun getAvailableLectures(studentId: Long, date: ZonedDateTime, currentTime: TimeHolder): List<LectureInfo> {
        lectureServiceValidator.validateAvailableLectures(date, currentTime)

        val student = studentService.getWithLectureEnrollment(studentId)
        val lecturesOnDate = lectureService.getAvailableLecturesOnDate(date)

        val availableLectureOnDate = lecturesOnDate.filter { !student.isEnrolledLecture(it.id!!) }

        return availableLectureOnDate.map { LectureInfo.from(it) }
    }
}