package com.micoder.bookstore.services.impl

import com.micoder.bookstore.repositories.AuthorRepository
import com.micoder.bookstore.testAuthorEntityA
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(
    private val authorServiceImpl: AuthorServiceImpl,
    private val authorRepository: AuthorRepository
) {

    @Test
    fun `test that save persists the Author in the database`() {
        val savedAuthor = authorServiceImpl.create(testAuthorEntityA())
        assertThat(savedAuthor.id).isNotNull()

        val recalledAuthor = authorRepository.findByIdOrNull(savedAuthor.id)
        assertThat(recalledAuthor).isNotNull()
        assertThat(recalledAuthor!!).isEqualTo(
            testAuthorEntityA(id = savedAuthor.id)
        )
    }

    @Test
    fun `test that list returns empty list when no authors in the database`() {
        val savedAuthors = authorServiceImpl.list()
        assertThat(savedAuthors).isEmpty()
    }

    @Test
    fun `test that list returns Authors when Authors present in the database`() {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val expected = listOf(savedAuthor)
        val result = authorServiceImpl.list()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test that get returns null when Author not present in the database`() {
        val result = authorServiceImpl.get(99)
        assertThat(result).isNull()
    }

    @Test
    fun `test that get Author when Author is present in the database`() {
        val savedAuthor = authorRepository.save(testAuthorEntityA())
        val result = authorServiceImpl.get(savedAuthor.id!!)
        assertThat(result).isEqualTo(savedAuthor)
    }

}