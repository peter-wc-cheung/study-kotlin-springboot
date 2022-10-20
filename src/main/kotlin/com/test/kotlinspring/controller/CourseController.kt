package com.test.kotlinspring.controller

import com.test.kotlinspring.dto.CourseDTO
import com.test.kotlinspring.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("v1/courses")
class CourseController(val courseService: CourseService) {

    @GetMapping
    fun getCourses(): ResponseEntity<List<CourseDTO>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.getCourses())
    }

    @PostMapping
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO): ResponseEntity<CourseDTO> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.addCourse(courseDTO))
    }

    @PutMapping("{courseId}")
    fun updateCourse(@PathVariable("courseId") courseId: Int,
                     @RequestBody @Valid courseDTO: CourseDTO): ResponseEntity<CourseDTO> = ResponseEntity
        .status(HttpStatus.OK)
        .body(courseService.updateCourse(courseId, courseDTO))

    @DeleteMapping("{courseId}")
    fun deleteCourse(@PathVariable("courseId") courseId: Int): ResponseEntity<Any> {
        courseService.deleteCourse(courseId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}