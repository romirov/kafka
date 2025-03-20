package com.orshlab.kafka.service

import com.orshlab.kafka.config.prop.TopicOwner
import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.util.JsonSerializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ConsumerService(
	val topicsProp: TopicsProp
) {

	private val _testDB = mutableListOf<Message>()

	@KafkaListener(
		id = "listen",
		topics = ["#{@topicsProp.topics[T(com.orshlab.kafka.config.prop.TopicOwner).CONSUMER]}"],
		containerFactory = "concurrentKafkaListenerContainerFactory"
	)
	fun listen(record: ConsumerRecord<Int, String>) {
		val msgFromKafka = record.value()
		logger.info("CONSUMER MESSAGE: $msgFromKafka, TOPIC: ${topicsProp.topics[TopicOwner.CONSUMER]}")
		val message = JsonSerializer.deserializeFromString(msgFromKafka)
		_testDB.add(message)
	}

	fun getMessage(): List<Message> = _testDB.toList()

	private companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}