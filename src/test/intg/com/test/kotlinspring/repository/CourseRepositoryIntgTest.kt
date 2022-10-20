package com.test.kotlinspring.repository

import com.test.kotlinspring.util.courseEntityList
import mu.KLogging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository;

    companion object {
        private const val ID = 100
        private const val NAME = "Build Restful APIs using SpringBoot and Kotlin"
        private const val CATEGORY = "Development"
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

}