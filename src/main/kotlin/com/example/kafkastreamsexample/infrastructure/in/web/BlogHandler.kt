// infrastructure/in/web/BlogHandler.kt
package com.example.kafkastreamsexample.infrastructure.`in`.web

import com.example.kafkastreamsexample.domain.port.`in`.CreateBlogUseCase
import com.example.kafkastreamsexample.infrastructure.`in`.web.dto.BlogRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class BlogHandler(
    private val createBlogUseCase: CreateBlogUseCase
) {
    fun createBlog(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { createBlogUseCase.createBlog(it) }
            .flatMap { blog ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(blog)
            }
    }

    fun getBlogs(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("Hello WebFlux!")
}
