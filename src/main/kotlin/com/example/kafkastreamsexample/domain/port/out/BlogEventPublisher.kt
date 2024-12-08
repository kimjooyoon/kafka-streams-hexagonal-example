package com.example.kafkastreamsexample.domain.port.out

import com.example.kafkastreamsexample.domain.event.BlogEvent
import reactor.core.publisher.Mono

interface BlogEventPublisher {
    fun publish(event: BlogEvent): Mono<Void>
}
