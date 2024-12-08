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
    override fun createBlog(blog: Blog): Mono<Blog> {
        val blogWithId = blog.copy(id = UUID.randomUUID().toString())
        val domainEvent = DomainEvent(
            id = blogWithId.id,
            eventType = "BlogCreated",
            payload = objectMapper.writeValueAsString(blogWithId)
        )

        return domainEventPort.save(domainEvent)
            .flatMap { savedEvent ->
                val blogEvent = BlogEvent(
                    id = blogWithId.id!!,
                    eventType = BlogEventType.CREATED,
                    data = objectMapper.writeValueAsString(blogWithId),
                    domainEventId = savedEvent.id!!
                )
                blogEventPublisher.publish(blogEvent)
                    .then(Mono.just(blogWithId))
                    .onErrorMap { error ->
                        BlogEventPublishException("Failed to publish blog event", error)
                    }
            }
    }

    override fun updateBlog(blog: Blog): Mono<Blog> {
        val domainEvent = DomainEvent(
            id = blog.id,
            eventType = "BlogUpdated",
            payload = objectMapper.writeValueAsString(blog)
        )

        return domainEventPort.save(domainEvent)
            .flatMap { savedEvent ->
                val blogEvent = BlogEvent(
                    id = blog.id!!,
                    eventType = BlogEventType.UPDATED,
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

    override fun deleteBlog(blog: Blog): Mono<Blog> {
        val domainEvent = DomainEvent(
            id = blog.id,
            eventType = "BlogDeleted",
            payload = objectMapper.writeValueAsString(blog)
        )

        return domainEventPort.save(domainEvent)
            .flatMap { savedEvent ->
                val blogEvent = BlogEvent(
                    id = blog.id!!,
                    eventType = BlogEventType.DELETED,
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
}

class BlogEventPublishException(message: String, cause: Throwable) : RuntimeException(message, cause)
