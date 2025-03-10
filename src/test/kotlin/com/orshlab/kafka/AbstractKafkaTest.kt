package com.orshlab.kafka

import com.orshlab.kafka.config.KafkaConfig
import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.service.Consumer
import com.orshlab.kafka.service.Producer
import com.orshlab.kafka.utils.Constants
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.kafka.ConfluentKafkaContainer

@SpringBootTest(classes = [KafkaConfig::class, TopicsProp::class, Consumer::class, Producer::class])
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
abstract class AbstractKafkaTest {
	private companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
		val kafkaContainer: ConfluentKafkaContainer = ConfluentKafkaContainer(
			Constants.KAFKA_DOCKER_IMAGE
		).also {
			it.withReuse(true)
			it.start()
			logger.info("Kafka container started...")
			logger.info("BOOTSTRAP SERVERs: ${it.bootstrapServers}")
		}

		@DynamicPropertySource
		@JvmStatic
		fun overrideProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers)
		}
	}
}