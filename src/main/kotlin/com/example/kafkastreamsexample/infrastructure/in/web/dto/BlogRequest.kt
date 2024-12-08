package com.example.kafkastreamsexample.infrastructure.`in`.web.dto

import com.example.kafkastreamsexample.domain.model.Blog

data class BlogRequest(
    val title: String,
    val content: String,
    val author: String
) {
    fun toDomain(): Blog = Blog(
        title = title,
        content = content,
        author = author
    )
}
