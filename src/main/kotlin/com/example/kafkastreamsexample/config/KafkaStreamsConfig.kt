package com.example.kafkastreamsexample.config

import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.BlogReadStorage
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams

@Configuration
@EnableKafkaStreams
class KafkaStreamsConfig(
    private val blogReadStorage: BlogReadStorage,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun processBlogEvents(streamsBuilder: StreamsBuilder): KStream<String, String> {
        val blogEvents: KStream<String, String> = streamsBuilder.stream("blog-events")

        blogEvents.foreach { _, event ->
            blogReadStorage.handleBlogEvent(objectMapper.readValue(event, BlogEvent::class.java))
                .subscribe()
        }

        return blogEvents
    }
}
