package com.micoder.bookstore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.micoder.bookstore.domain.entities.AuthorEntity
import com.micoder.bookstore.services.AuthorService
import com.micoder.bookstore.testAuthorDtoA
import com.micoder.bookstore.testAuthorEntityA
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

private const val AUTHORS_BASE_URL = "/v1/authors"

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor (
    private val mockMvc: MockMvc,
    @MockkBean val authorService: AuthorService
) {

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun beforeEach() {
        every {
            authorService.create(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `test that create author saves the Author`() {
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }

        val expected = AuthorEntity(
            id = null,
            name = "test name",
            age = 24,
            description = "some desc",
            image = "auth.jpg"
        )

        verify { authorService.create(expected) }
    }

    @Test
    fun `test that create Author returns a HTTP 201 status on a successful create`() {
        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test that list returns an empty list and HTTP 200 when no authors in the database`() {
        every {
            authorService.list()
        } answers {
            emptyList()
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `test that list returns Authors and HTTP 200 when authors in the database`() {
        every {
            authorService.list()
        } answers {
            listOf(testAuthorEntityA(id = 1))
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", equalTo(1)) }
            content { jsonPath("$[0].name", equalTo("test name")) }
            content { jsonPath("$[0].age", equalTo(24)) }
            content { jsonPath("$[0].description", equalTo("some desc")) }
            content { jsonPath("$[0].image", equalTo("auth.jpg")) }
        }
    }

    @Test
    fun `test that get returns HTTP 404 when author not found in database`() {
        every {
            authorService.get(any())
        } answers {
            null
        }

        mockMvc.get("${AUTHORS_BASE_URL}/99") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `test that get returns HTTP 200 and author when author found in database`() {
        every {
            authorService.get(any())
        } answers {
            testAuthorEntityA(id = 99)
        }

        mockMvc.get("${AUTHORS_BASE_URL}/99") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(99)) }
            content { jsonPath("$.name", equalTo("test name")) }
            content { jsonPath("$.age", equalTo(24)) }
            content { jsonPath("$.description", equalTo("some desc")) }
            content { jsonPath("$.image", equalTo("auth.jpg")) }
        }
    }

}