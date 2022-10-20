package com.test.kotlinspring.entity

import javax.persistence.*

@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,
    var name: String,
    var category: String,
)
