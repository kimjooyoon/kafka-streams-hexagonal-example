package com.example.kafkastreamsexample.infrastructure.`in`.web

import com.example.kafkastreamsexample.application.service.BlogEventPublishException
import com.example.kafkastreamsexample.domain.model.Blog
import com.example.kafkastreamsexample.domain.port.`in`.BlogUseCase
import com.example.kafkastreamsexample.infrastructure.`in`.web.dto.BlogRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class BlogHandler(
    private val blogUseCase: BlogUseCase
) {
    private fun createResponse(mono: Mono<Blog>): Mono<ServerResponse> =
        mono.flatMap { blog ->
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(blog)
        }.onErrorResume { error ->
            when (error) {
                is IllegalArgumentException -> ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(ErrorResponse("Invalid request", error.message))
                is BlogEventPublishException -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(ErrorResponse("Failed to process blog event", error.message))
                else -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(ErrorResponse("Internal server error", error.message))
            }
        }

    fun createBlog(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.createBlog(it) }
            .transform(::createResponse)

    fun updateBlog(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.updateBlog(it) }
            .transform(::createResponse)

    fun deleteBlog(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(BlogRequest::class.java)
            .map { it.toDomain() }
            .flatMap { blogUseCase.deleteBlog(it) }
            .transform(::createResponse)

    fun getBlogs(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("Hello WebFlux!")

    data class ErrorResponse(
        val error: String,
        val message: String?
    )
}
