package com.test.kotlinspring.controller

import com.test.kotlinspring.dto.CourseDTO
import com.test.kotlinspring.entity.Course
import com.test.kotlinspring.repository.CourseRepository
import com.test.kotlinspring.util.courseEntityList
import io.mockk.verify
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository;

    companion object : KLogging()

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courseEntityList())
    }

    @Test
    fun getCourses() {
        val result = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals(3, result!!.size)
    }


    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")

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

        // existing course
        val course = Course(null, "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)
        logger.info { "courseID: ${course.id}" }

        // courseID
        val updatedCourseDTO = CourseDTO(null, "Build RestFul APis using SpringBoot and Kotlin ABC", "Development")

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        // updated courseDTO
        assertEquals(updatedCourse!!.name, updatedCourseDTO.name)

    }

    @Test
    fun deleteCourse() {

        // existing course
        val course = Course(null, "Build RestFul APis using SpringBoot and Kotlin", "Development")
        courseRepository.save(course)
        logger.info { "courseID: ${course.id}" }

        // courseID
        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent


    }

}