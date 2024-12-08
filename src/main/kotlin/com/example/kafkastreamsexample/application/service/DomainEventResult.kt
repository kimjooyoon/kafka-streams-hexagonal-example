package com.example.kafkastreamsexample.application.service

sealed class DomainEventResult<out T> {
    data class Success<T>(val data: T) : DomainEventResult<T>()
    data class Failure(val error: Throwable) : DomainEventResult<Nothing>()
}
