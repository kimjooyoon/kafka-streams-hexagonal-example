package com.example.kafkastreamsexample.domain.port.out

import com.example.kafkastreamsexample.domain.model.DomainEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface DomainEventPort {
    fun save(domainEvent: DomainEvent): Mono<DomainEvent>

    fun findAllByOrderByCreatedAtAsc(): Flux<DomainEvent>
    fun findAllByCreatedAtAfterOrderByCreatedAtAsc(timestamp: LocalDateTime): Flux<DomainEvent>
}