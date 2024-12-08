package com.example.kafkastreamsexample.domain.port.`in`

import com.example.kafkastreamsexample.domain.model.Blog
import reactor.core.publisher.Mono

interface CreateBlogUseCase {
    fun createBlog(blog: Blog): Mono<Blog>
}
