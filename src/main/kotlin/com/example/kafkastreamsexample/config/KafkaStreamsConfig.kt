package com.example.kafkastreamsexample.config

import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.infrastructure.out.persistence.postgres.BlogReadStorage
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import reactor.core.publisher.Mono
import java.time.Duration

@Configuration
@EnableKafkaStreams
class KafkaStreamsConfig(
    private val blogReadStorage: BlogReadStorage,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun processBlogEvents(streamsBuilder: StreamsBuilder): KStream<String, String> {
        val blogEvents: KStream<String, String> = streamsBuilder
            .stream("blog-events", Consumed.with(Serdes.String(), Serdes.String()))

        blogEvents.foreach { key, event ->
            try {
                val blogEvent = objectMapper.readValue(event, BlogEvent::class.java)

                blogReadStorage.handleBlogEvent(blogEvent)
                    .timeout(Duration.ofSeconds(5))
                    .doOnSuccess {
                        logger.info("Successfully processed blog event: ${blogEvent.id}")
                    }
                    .doOnError { error ->
                        logger.error("Failed to process blog event: ${blogEvent.id}", error)
                    }
                    .onErrorResume { error ->
                        logger.error("Error processing blog event: ${blogEvent.id}", error)
                        Mono.empty()
                    }
                    .block()
            } catch (e: Exception) {
                logger.error("Failed to parse or process blog event", e)
            }
        }

        blogEvents.to("blog-read-model")

        return blogEvents
    }
}
