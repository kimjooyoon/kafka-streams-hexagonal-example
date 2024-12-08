package com.example.kafkastreamsexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaStreamsExampleApplication

fun main(args: Array<String>) {
    runApplication<KafkaStreamsExampleApplication>(*args)
}
