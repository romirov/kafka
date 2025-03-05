package com.orshlab.kafka.config

import com.orshlab.kafka.config.prop.TopicsProp
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

@Configuration
@EnableKafka
class KafkaConfig(
	private val properties: KafkaProperties,
	private val topicsProp: TopicsProp
) {
	val consumerProps = mapOf(
		ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
		ConsumerConfig.GROUP_ID_CONFIG to properties.consumer.groupId,
		ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to properties.consumer.autoOffsetReset,
		ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class.java,
		ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
		ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG to "org.apache.kafka.clients.consumer.RoundRobinAssignor",
		ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to "1000",
	)

	@Bean
	fun consumerFactory() = DefaultKafkaConsumerFactory<Int, String>(consumerProps)

	@Bean
	fun concurrentKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<Int, String>): ConcurrentKafkaListenerContainerFactory<Int, String> =
		ConcurrentKafkaListenerContainerFactory<Int, String>()
			.also {
				it.consumerFactory = consumerFactory
				it.setConcurrency(3)
				it.containerProperties.pollTimeout = 3000
				it.createContainer(*topicsProp.topics.toTypedArray())
			}

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