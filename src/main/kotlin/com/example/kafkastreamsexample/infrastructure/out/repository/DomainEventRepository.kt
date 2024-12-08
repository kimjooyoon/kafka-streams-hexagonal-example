package com.example.kafkastreamsexample.infrastructure.out.repository

import com.example.kafkastreamsexample.domain.model.DomainEvent
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DomainEventRepository : ReactiveMongoRepository<DomainEvent, String>
