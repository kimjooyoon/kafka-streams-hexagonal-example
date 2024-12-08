package com.example.kafkastreamsexample.application.service

import com.example.kafkastreamsexample.domain.model.Blog
import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.domain.event.BlogEventType
import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.`in`.CreateBlogUseCase
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
) : CreateBlogUseCase {
    override fun createBlog(blog: Blog): Mono<Blog> {
        val blogWithId = blog.copy(id = UUID.randomUUID().toString())

        return domainEventPort.save(
            DomainEvent(
                id = blogWithId.id,
                eventType = "BlogCreated",
                payload = objectMapper.writeValueAsString(blogWithId)
            )
        )
            .then(
                Mono.defer {
                    val event = BlogEvent(
                        id = blogWithId.id!!,
                        eventType = BlogEventType.CREATED,
                        data = objectMapper.writeValueAsString(blogWithId)
                    )
                    blogEventPublisher.publish(event)
                }
            )
            .thenReturn(blogWithId)
    }
}