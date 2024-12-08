package com.example.kafkastreamsexample.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "domain_events")
data class DomainEvent(
    @Id
    val id: String? = null,
    val eventType: String,
    val payload: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
