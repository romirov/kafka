package com.orshlab.kafka.service

import com.orshlab.kafka.config.prop.TopicOwner
import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.util.JsonSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ProducerService(
	private val template: KafkaTemplate<Int, String>,
	private val topicsProp: TopicsProp
) {
	fun send(message: Message, key: Int) {
		val serializedMsg = JsonSerializer.serializeToString(message)
		topicsProp.topics
			.getValue(TopicOwner.PRODUCER)
			.forEach { topic ->
				logger.info("PRODUCER MESSAGE: $serializedMsg, TOPIC: $topic")
				template.send(topic, key, serializedMsg)
			}
	}

	private companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}