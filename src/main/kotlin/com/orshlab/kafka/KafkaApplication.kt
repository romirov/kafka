package com.orshlab.kafka

import com.orshlab.kafka.config.prop.TopicsProp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(TopicsProp::class)
class KafkaApplication

fun main(args: Array<String>) {
	runApplication<KafkaApplication>(*args)
}
