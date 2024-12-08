package com.example.kafkastreamsexample.application.service

import com.example.kafkastreamsexample.domain.model.Blog
import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.`in`.CreateBlogUseCase
import com.example.kafkastreamsexample.domain.port.out.DomainEventPort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BlogService(
    private val domainEventPort: DomainEventPort
) : CreateBlogUseCase {
    override fun createBlog(blog: Blog): Mono<Blog> {
        return domainEventPort.save(
            DomainEvent(
                eventType = "BlogCreated",
                payload = blog.toString()
            )
        ).map { blog }
    }
}
