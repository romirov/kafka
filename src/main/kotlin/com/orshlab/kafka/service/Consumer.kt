package com.orshlab.kafka.service

import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.util.JsonSerializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class Consumer(
	val topicsProp: TopicsProp
) {

	private val _testDB = mutableListOf<Message>()

	@KafkaListener(
		id = "listen",
		topics = ["#{@topicsProp.topics}"],
		containerFactory = "concurrentKafkaListenerContainerFactory"
	)
	fun listen(record: ConsumerRecord<Int, String>) {
		val msgFromKafka = record.value()
		println(msgFromKafka)
		val message = JsonSerializer.deserializeFromString(msgFromKafka)
		_testDB.add(message)
	}

	fun getMessage(): List<Message> = _testDB.toList()
}