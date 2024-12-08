package com.example.kafkastreamsexample.infrastructure.out.persistence

import com.example.kafkastreamsexample.domain.model.DomainEvent
import com.example.kafkastreamsexample.domain.port.out.DomainEventPort
import com.example.kafkastreamsexample.infrastructure.out.repository.DomainEventRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Component
class DomainEventPersistenceAdapter(
    private val domainEventRepository: DomainEventRepository
) : DomainEventPort {
    override fun save(domainEvent: DomainEvent): Mono<DomainEvent> {
        return domainEventRepository.save(domainEvent)
    }

    override fun findAllByOrderByCreatedAtAsc(): Flux<DomainEvent> {
        return domainEventRepository.findAllByOrderByCreatedAtAsc()
    }

    override fun findAllByCreatedAtAfterOrderByCreatedAtAsc(timestamp: LocalDateTime): Flux<DomainEvent> {
        return domainEventRepository.findAllByCreatedAtAfterOrderByCreatedAtAsc(timestamp)
    }
}