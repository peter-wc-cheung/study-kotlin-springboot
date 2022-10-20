package com.test.kotlinspring.repository

import com.test.kotlinspring.entity.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String): List<Course>


}