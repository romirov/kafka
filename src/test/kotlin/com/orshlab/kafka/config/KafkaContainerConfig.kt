package com.orshlab.kafka.config

import com.orshlab.kafka.utils.Constants.KAFKA_DOCKER_IMAGE
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.kafka.ConfluentKafkaContainer


@TestConfiguration
class KafkaContainerConfig {
	@Bean
	fun kafkaContainer(): ConfluentKafkaContainer = ConfluentKafkaContainer(KAFKA_DOCKER_IMAGE)
		.withExposedPorts(9092)
		.withReuse(true)

	@DynamicPropertySource
	fun overrideProperties(registry: DynamicPropertyRegistry) {
		registry.add("spring.kafka.bootstrap-servers", kafkaContainer()::getBootstrapServers)
	}
}