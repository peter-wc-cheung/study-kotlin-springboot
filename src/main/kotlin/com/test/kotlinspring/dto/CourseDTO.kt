package com.test.kotlinspring.dto

import javax.validation.constraints.NotBlank

data class CourseDTO (
    var id: Int?,
    @get:NotBlank(message = "name must not be blank.")
    var name: String,
    @get:NotBlank(message = "category must not be blank.")
    var category: String,
)