package com.test.kotlinspring

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import com.test.kotlinspring.service.GreetingService
import mu.KLogging
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingService: GreetingService

    companion object : KLogging()

    @Test
    fun retrieveGreeting() {

        val name = "Peter"

        every { greetingService.retrieveGreeting(any()) } returns "Hello $name"


        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult();

        Assertions.assertEquals("Hello $name, helloworld", result.responseBody);

    }



}