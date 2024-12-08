package com.example.kafkastreamsexample.domain.port.`in`

import com.example.kafkastreamsexample.domain.model.Blog
import reactor.core.publisher.Mono

interface BlogUseCase {
    fun createBlog(blog: Blog): Mono<Blog>
    fun updateBlog(blog: Blog): Mono<Blog>
    fun deleteBlog(blog: Blog): Mono<Blog>
}
