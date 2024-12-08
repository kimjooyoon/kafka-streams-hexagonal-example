package com.example.kafkastreamsexample.domain.model

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Blog(
    @Id
    val id: String? = null,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
