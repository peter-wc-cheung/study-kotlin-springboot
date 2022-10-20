package com.test.kotlinspring.repository

import com.test.kotlinspring.util.courseEntityList
import mu.KLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository;

    companion object {
        private const val ID = 100
        private const val NAME = "Build Restful APIs using SpringBoot and Kotlin"
        private const val CATEGORY = "Development"

        // need to convert to static method in JAVA
        @JvmStatic
        private fun courseAndSize(): Stream<Arguments> {

            return Stream.of(Arguments.arguments("SpringBoot", 2))

        }

        private val logger = KLogging().logger
    }

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        courseRepository.saveAll(courseEntityList())
    }

    @Test
    fun findByNameContaining() {

        val courses = courseRepository.findByNameContaining("SpringBoot")
        logger.info { "courses: ${courses}" }

        assertEquals(2, courses.size)

    }

    @Test
    fun findCoursesByName() {

        val courses = courseRepository.findCoursesByName("SpringBoot")
        logger.info { "courses: ${courses}" }

        assertEquals(2, courses.size)

    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(name: String, expectedSize: Int) {

        val courses = courseRepository.findCoursesByName(name)
        logger.info { "courses: ${courses}" }

        assertEquals(expectedSize, courses.size)

    }

}