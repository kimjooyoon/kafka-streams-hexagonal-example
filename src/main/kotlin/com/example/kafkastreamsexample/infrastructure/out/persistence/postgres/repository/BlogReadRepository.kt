package com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.repository

import com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.entity.BlogReadEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BlogReadRepository : ReactiveCrudRepository<BlogReadEntity, String> {
    fun findByAuthor(author: String): Flux<BlogReadEntity>

    fun findByTitleContaining(titleKeyword: String): Flux<BlogReadEntity>

    @Query("SELECT * FROM blogs WHERE status = 'ACTIVE' ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    fun findRecentBlogs(limit: Int, offset: Long): Flux<BlogReadEntity>
}
