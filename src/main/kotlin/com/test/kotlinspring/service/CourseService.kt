package com.test.kotlinspring.service

import com.test.kotlinspring.CourseNotFoundException
import com.test.kotlinspring.dto.CourseDTO
import com.test.kotlinspring.entity.Course
import com.test.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CourseService(var courseRepository: CourseRepository) {

    companion object : KLogging()

    @Transactional(readOnly = true)
    fun getCourses(): List<CourseDTO> {
        return courseRepository.findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }
    }

    @Transactional(readOnly = false)
    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        var entity = courseDTO.let {
            Course(null, it.name, it.category)
        }

        courseRepository.save(entity)

        logger.info { "Saved course is : $entity" }

        return entity.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    @Transactional(readOnly = false)
    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(courseId)

        return if(existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    CourseDTO(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("No course found the passing ID: $courseId")
        }
    }

    @Transactional(readOnly = false)
    fun deleteCourse(courseId: Int): Any {
        val existingCourse = courseRepository.findById(courseId)

        return if(existingCourse.isPresent) {
            courseRepository.deleteById(courseId)
        } else {
            throw CourseNotFoundException("No course found the passing ID: $courseId")
        }
    }

}