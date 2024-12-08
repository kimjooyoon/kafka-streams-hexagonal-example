package com.example.kafkastreamsexample.domain.port.out

import com.example.kafkastreamsexample.domain.model.DomainEvent
import reactor.core.publisher.Mono

interface DomainEventPort {
    fun save(domainEvent: DomainEvent): Mono<DomainEvent>
}