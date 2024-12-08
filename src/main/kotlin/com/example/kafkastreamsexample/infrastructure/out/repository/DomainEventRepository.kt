package com.example.kafkastreamsexample.infrastructure.out.repository

import com.example.kafkastreamsexample.domain.model.DomainEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface DomainEventRepository : ReactiveMongoRepository<DomainEvent, String>{
    fun findAllByOrderByCreatedAtAsc(): Flux<DomainEvent>

    fun findAllByCreatedAtAfterOrderByCreatedAtAsc(createdAt: LocalDateTime): Flux<DomainEvent>
}
