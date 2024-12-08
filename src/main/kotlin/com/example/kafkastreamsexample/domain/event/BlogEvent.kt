package com.example.kafkastreamsexample.domain.event

import java.time.Instant

data class BlogEvent(
    val id: String,
    val eventType: BlogEventType,
    val data: String,
    val timestamp: Instant = Instant.now(),
    val domainEventId: String
)

enum class BlogEventType {
    CREATED, UPDATED, DELETED
}
