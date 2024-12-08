package com.example.kafkastreamsexample.application.service

import com.example.kafkastreamsexample.domain.model.Blog
import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.domain.event.BlogEventType
import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.`in`.BlogUseCase
import com.example.kafkastreamsexample.domain.port.out.BlogEventPublisher
import com.example.kafkastreamsexample.domain.port.out.DomainEventPort
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID


@Service
class BlogService(
    private val blogEventPublisher: BlogEventPublisher,
    private val objectMapper: ObjectMapper,
    private val domainEventPort: DomainEventPort
) : BlogUseCase {
    private fun publishBlogEvent(
        blog: Blog,
        eventType: String,
        blogEventType: BlogEventType
    ): Mono<Blog> {
        val domainEvent = DomainEvent(
            id = UUID.randomUUID().toString(),
            eventType = eventType,
            payload = objectMapper.writeValueAsString(blog)
        )

        return domainEventPort.save(domainEvent)
            .flatMap { savedEvent ->
                val blogEvent = BlogEvent(
                    id = blog.id!!,
                    eventType = blogEventType,
                    data = objectMapper.writeValueAsString(blog),
                    domainEventId = savedEvent.id!!
                )
                blogEventPublisher.publish(blogEvent)
                    .then(Mono.just(blog))
                    .onErrorMap { error ->
                        BlogEventPublishException("Failed to publish blog event", error)
                    }
            }
    }

    override fun createBlog(blog: Blog): Mono<Blog> {
        val blogWithId = blog.copy(id = UUID.randomUUID().toString())
        return publishBlogEvent(blogWithId, "BlogCreated", BlogEventType.CREATED)
    }

    override fun updateBlog(blog: Blog): Mono<Blog> {
        return publishBlogEvent(blog, "BlogUpdated", BlogEventType.UPDATED)
    }

    override fun deleteBlog(blog: Blog): Mono<Blog> {
        return publishBlogEvent(blog, "BlogDeleted", BlogEventType.DELETED)
    }
}

class BlogEventPublishException(message: String, cause: Throwable) : RuntimeException(message, cause)
