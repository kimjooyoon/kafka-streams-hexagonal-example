package com.example.kafkastreamsexample.infrastructure.out.persistence.postgres

import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.domain.event.BlogEventType
import com.example.kafkastreamsexample.domain.model.Blog
import com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.entity.BlogReadEntity
import com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.repository.BlogReadRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Component
class BlogReadStorage(
    private val blogReadRepository: BlogReadRepository,
    private val objectMapper: ObjectMapper
) {
    fun handleBlogEvent(event: BlogEvent): Mono<BlogReadEntity> {
        val blog = objectMapper.readValue(event.data, Blog::class.java)

        return when (event.eventType) {
            BlogEventType.CREATED -> createBlog(blog)
            BlogEventType.UPDATED -> updateBlog(blog)
            BlogEventType.DELETED -> deleteBlog(blog.id!!)
        }
    }

    private fun createBlog(blog: Blog): Mono<BlogReadEntity> {
        val entity = BlogReadEntity(
            id = blog.id!!,
            title = blog.title,
            content = blog.content,
            author = blog.author,
            createdAt = blog.createdAt
        )
        entity.newBlog = true
        return blogReadRepository.save(entity)
    }

    private fun updateBlog(blog: Blog): Mono<BlogReadEntity> {
        return blogReadRepository.findById(blog.id!!)
            .map { existing ->
                existing.copy(
                    id = existing.id!!,
                    title = blog.title,
                    content = blog.content,
                    author = existing.author,
                    createdAt = existing.createdAt,
                    updatedAt = LocalDateTime.now(),
                    status = existing.status,
                    version = existing.version + 1,
                )
            }
            .flatMap { blogReadRepository.save(it) }
    }

    private fun deleteBlog(id: String): Mono<BlogReadEntity> {
        return blogReadRepository.findById(id)
            .map {
                it.copy(status = "DELETED", updatedAt = LocalDateTime.now())
            }
            .flatMap { blogReadRepository.save(it) }
    }
}