package com.example.kafkastreamsexample.infrastructure.out.messaging

import com.example.kafkastreamsexample.domain.event.BlogEvent
import com.example.kafkastreamsexample.domain.port.out.BlogEventPublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

@Component
class KafkaBlogEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, BlogEvent>
) : BlogEventPublisher {
    override fun publish(event: BlogEvent): Mono<Void> {
        return Mono.defer {
            val future: CompletableFuture<Void> = kafkaTemplate
                .send("blog-events", event.id, event)
                .thenAccept { }
            Mono.fromFuture { future }
        }
    }
}
