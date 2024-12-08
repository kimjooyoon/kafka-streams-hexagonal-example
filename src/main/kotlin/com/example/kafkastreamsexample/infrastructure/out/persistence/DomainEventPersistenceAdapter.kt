package com.example.kafkastreamsexample.infrastructure.out.persistence

import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.out.DomainEventPort
import com.example.kafkastreamsexample.infrastructure.out.repository.DomainEventRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DomainEventPersistenceAdapter(
    private val domainEventRepository: DomainEventRepository
) : DomainEventPort {
    override fun save(domainEvent: DomainEvent): Mono<DomainEvent> {
        return domainEventRepository.save(domainEvent)
    }
}