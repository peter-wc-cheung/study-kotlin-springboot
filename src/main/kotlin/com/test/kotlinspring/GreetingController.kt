package com.test.kotlinspring

import com.test.kotlinspring.service.GreetingService
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/greetings")
class GreetingController(val greetingService: GreetingService) {

    companion object : KLogging()

    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @GetMapping("{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String {

        logger.info { "name: $name" }

        // return "Hello $name"
        return this.greetingService.retrieveGreeting(name) + ", $applicationName"

    }

}