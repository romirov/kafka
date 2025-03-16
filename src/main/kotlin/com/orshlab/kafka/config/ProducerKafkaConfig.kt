package com.orshlab.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@EnableKafka
class ProducerKafkaConfig(
	private val properties: KafkaProperties
) {

	val producerProps = mapOf(
		ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
		ProducerConfig.ACKS_CONFIG to properties.producer.acks,
		ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to IntegerSerializer::class.java,
		ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
	)

	@Bean
	fun kafkaTemplate(producerFactory: ProducerFactory<Int, String>) = KafkaTemplate(producerFactory)

	@Bean
	fun producerFactory() = DefaultKafkaProducerFactory<Int, String>(producerProps)
}