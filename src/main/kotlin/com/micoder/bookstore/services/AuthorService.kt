package com.micoder.bookstore.services

import com.micoder.bookstore.domain.AuthorUpdateRequest
import com.micoder.bookstore.domain.entities.AuthorEntity

interface AuthorService {

    fun create(authorEntity: AuthorEntity): AuthorEntity

    fun list(): List<AuthorEntity>

    fun get(id: Long): AuthorEntity?

    fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity

    fun partialUpdate(id: Long, authorUpdateRequest: AuthorUpdateRequest): AuthorEntity

    fun delete(id: Long)

}