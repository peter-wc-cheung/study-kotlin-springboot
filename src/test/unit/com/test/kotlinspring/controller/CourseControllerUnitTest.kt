package com.test.kotlinspring.controller

import com.ninjasquad.springmockk.MockkBean
import com.test.kotlinspring.dto.CourseDTO
import com.test.kotlinspring.entity.Course
import com.test.kotlinspring.service.CourseService
import io.mockk.every
import io.mockk.justRun
import io.mockk.runs
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    companion object {
        private const val ID = 100
        private const val NAME = "Build Restful APIs using Springboot and Kotlin"
        private const val CATEGORY = "Development"
        private val logger = KLogging().logger
    }

    @Test
    fun getCourses() {

        logger.info { 123 }

        every { courseService.getCourses() } returns listOf(
            CourseDTO(1, NAME, CATEGORY),
            CourseDTO(2, NAME, CATEGORY),
            CourseDTO(3, NAME, CATEGORY)
        )

        val result = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(3, result!!.size)
    }

    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(null, NAME, CATEGORY)

        every { courseService.addCourse(any()) } returns CourseDTO(ID, NAME, CATEGORY)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue{
            savedCourseDTO!!.id != null
        }

    }

    @Test
    fun updateCourse() {

        every { courseService.updateCourse(any(), any()) } returns CourseDTO(100, NAME, CATEGORY)

        // courseID
        val updatedCourseDTO = CourseDTO(ID, NAME, CATEGORY)

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", ID)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // updated courseDTO
        Assertions.assertEquals(updatedCourse!!.name, NAME)

    }

    @Test
    fun deleteCourse() {

        justRun { courseService.deleteCourse(any()) }

        // courseID
        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", ID)
            .exchange()
            .expectStatus().isNoContent


    }

}