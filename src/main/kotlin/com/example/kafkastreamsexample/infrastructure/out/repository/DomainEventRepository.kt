package com.example.kafkastreamsexample.infrastructure.out.repository

import com.example.kafkastreamsexample.domain.model.DomainEvent
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DomainEventRepository : MongoRepository<DomainEvent, String>
