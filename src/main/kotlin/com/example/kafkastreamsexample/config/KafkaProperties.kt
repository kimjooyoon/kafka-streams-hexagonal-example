// config/KafkaProperties.kt
package com.example.kafkastreamsexample.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka")
data class KafkaProperties(
    val topics: Topics
) {
    data class Topics(
        val input: String,
        val output: String
    )
}
