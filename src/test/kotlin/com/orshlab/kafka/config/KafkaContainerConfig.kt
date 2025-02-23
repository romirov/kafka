package com.orshlab.kafka.config

import com.orshlab.kafka.utils.Constants.KAFKA_DOCKER_IMAGE
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class KafkaContainerConfig {
	@Bean
	fun kafkaContainer(): KafkaContainer = KafkaContainer(DockerImageName.parse(KAFKA_DOCKER_IMAGE))
		.withExposedPorts(9092)
		.withReuse(true)
}