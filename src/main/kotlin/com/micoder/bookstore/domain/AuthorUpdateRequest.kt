package com.micoder.bookstore.domain

data class AuthorUpdateRequest(
    val id: Long?,
    val name: String?,
    val age: Int?,
    val description: String?,
    val image: String?
)
