package com.micoder.bookstore

import com.micoder.bookstore.domain.dto.AuthorDto
import com.micoder.bookstore.domain.entities.AuthorEntity

fun testAuthorDtoA(id: Long? = null) = AuthorDto (
    id = id,
    name = "test name",
    age = 24,
    description = "some desc",
    image = "auth.jpg"
)

fun testAuthorEntityA(id: Long? = null) = AuthorEntity (
    id = id,
    name = "test name",
    age = 24,
    description = "some desc",
    image = "auth.jpg"
)