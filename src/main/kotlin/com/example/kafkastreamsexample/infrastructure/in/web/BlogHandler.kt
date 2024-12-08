package com.example.kafkastreamsexample.infrastructure.`in`.web

import com.example.kafkastreamsexample.domain.port.`in`.BlogUseCase
import com.example.kafkastreamsexample.infrastructure.`in`.web.dto.BlogRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class BlogHandler(
    private val blogUseCase: BlogUseCase
) {
    fun createBlog(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.createBlog(it) }
            .flatMap { blog ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(blog)
            }
    }

    fun updateBlog(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.updateBlog(it) }
            .flatMap { blog ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(blog)
            }
    }

    fun deleteBlog(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.deleteBlog(it) }
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
