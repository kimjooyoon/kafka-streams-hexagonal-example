// config/KafkaConfig.kt
package com.example.kafkastreamsexample.config

import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.out.DomainEventPort
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import java.util.UUID

@Configuration
@EnableKafkaStreams
class KafkaConfig(
    private val domainEventPort: DomainEventPort,
    private val kafkaProperties: KafkaProperties
) {
    @Bean
    fun kStream(streamsBuilder: StreamsBuilder): KStream<String, String> {
        val inputStream: KStream<String, String> = streamsBuilder.stream(kafkaProperties.topics.input)

        val processedStream = inputStream.mapValues { value ->
            val event = DomainEvent(
                id = UUID.randomUUID().toString(),
                eventType = "MessageProcessed",
                payload = value
            )
            domainEventPort.save(event).block()
            "processed: $value"
        }

        processedStream.to(kafkaProperties.topics.output)

        return processedStream
    }
}
