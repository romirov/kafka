package com.orshlab.kafka.config

import com.orshlab.kafka.config.prop.TopicOwner
import com.orshlab.kafka.config.prop.TopicsProp
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class ConsumerKafkaConfig(
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
		ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to properties.consumer.enableAutoCommit,
		ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to "1000",
	)

	@Bean
	fun consumerFactory() = DefaultKafkaConsumerFactory<Int, String>(consumerProps)

	@Bean
	fun concurrentKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<Int, String>): ConcurrentKafkaListenerContainerFactory<Int, String> =
		ConcurrentKafkaListenerContainerFactory<Int, String>()
			.also { containerFactory ->
				containerFactory.consumerFactory = consumerFactory
				containerFactory.setConcurrency(3)
				containerFactory.containerProperties.pollTimeout = 3000
				topicsProp.topics[TopicOwner.CONSUMER]?.let { topics ->
					containerFactory.createContainer(*topics.toTypedArray())
				}
			}
}