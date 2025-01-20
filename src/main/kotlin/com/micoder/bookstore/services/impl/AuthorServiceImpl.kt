package com.micoder.bookstore.services.impl

import com.micoder.bookstore.domain.AuthorUpdateRequest
import com.micoder.bookstore.domain.entities.AuthorEntity
import com.micoder.bookstore.repositories.AuthorRepository
import com.micoder.bookstore.services.AuthorService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {

    override fun create(authorEntity: AuthorEntity): AuthorEntity {
        require(null == authorEntity.id)
        return authorRepository.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepository.findAll()
    }

    override fun get(id: Long): AuthorEntity? {
        return authorRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, authorEntity: AuthorEntity): AuthorEntity {
        check(authorRepository.existsById(id))
        val normalisedAuthor = authorEntity.copy(id = id)
        return authorRepository.save(normalisedAuthor)
    }

    @Transactional
    override fun partialUpdate(id: Long, authorUpdateRequest: AuthorUpdateRequest): AuthorEntity {
        val existsAuthor = authorRepository.findByIdOrNull(id)
        checkNotNull(existsAuthor)

        val updateAuthor = existsAuthor.copy(
            name = authorUpdateRequest.name ?: existsAuthor.name,
            age = authorUpdateRequest.age ?: existsAuthor.age,
            description = authorUpdateRequest.description ?: existsAuthor.description,
            image = authorUpdateRequest.image ?: existsAuthor.image,
        )

        return authorRepository.save(updateAuthor)
    }

    override fun delete(id: Long) {
        authorRepository.deleteById(id)
    }

}