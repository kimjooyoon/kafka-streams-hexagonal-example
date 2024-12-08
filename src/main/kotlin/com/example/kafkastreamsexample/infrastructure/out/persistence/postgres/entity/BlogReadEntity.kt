package com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import org.springframework.data.annotation.Transient

@Table("blogs")
data class BlogReadEntity(
    @Id @JvmField
    var id: String? = null,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val status: String = "ACTIVE",
    val version: Long = 1
) : Persistable<String> {
    @Transient
    @JvmField
    var newBlog: Boolean = false

    override fun getId(): String? {
        return id
    }

    override fun isNew(): Boolean {
        return newBlog
    }
}
