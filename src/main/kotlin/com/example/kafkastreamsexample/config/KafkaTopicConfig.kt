package com.example.kafkastreamsexample.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig(
    @Value("\${kafka.topics.input.name}") private val inputTopic: String,
    @Value("\${kafka.topics.output.name}") private val outputTopic: String,
    @Value("\${kafka.topics.input.partitions}") private val inputPartitions: Int,
    @Value("\${kafka.topics.output.partitions}") private val outputPartitions: Int,
    @Value("\${kafka.topics.input.replicas}") private val inputReplicas: Int,
    @Value("\${kafka.topics.output.replicas}") private val outputReplicas: Int
) {

    @Bean
    fun inputTopic(): NewTopic {
        return TopicBuilder.name(inputTopic)
            .partitions(inputPartitions)
            .replicas(inputReplicas)
            .build()
    }

    @Bean
    fun outputTopic(): NewTopic {
        return TopicBuilder.name(outputTopic)
            .partitions(outputPartitions)
            .replicas(outputReplicas)
            .build()
    }
}
