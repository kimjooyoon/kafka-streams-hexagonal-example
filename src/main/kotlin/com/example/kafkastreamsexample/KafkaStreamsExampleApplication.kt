package com.example.kafkastreamsexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KafkaStreamsExampleApplication

fun main(args: Array<String>) {
    runApplication<KafkaStreamsExampleApplication>(*args)
}
