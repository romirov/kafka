package com.orshlab.kafka

import com.ocadotechnology.gembus.test.Arranger.some
import com.orshlab.kafka.config.KafkaConfig
import com.orshlab.kafka.config.KafkaContainerConfig
import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.service.Consumer
import com.orshlab.kafka.service.Producer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [KafkaConfig::class, KafkaContainerConfig::class, TopicsProp::class, Consumer::class, Producer::class])
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaTest {
	@Autowired
	lateinit var topicsProp: TopicsProp

	@Autowired
	lateinit var consumer: Consumer

	@Autowired
	lateinit var producer: Producer

	@Test
	fun test() {
		producer.send(msg, 1)
		val receivedMsgs = consumer.getMessage()
		println(consumer.getMessage())
		Assertions.assertEquals(1, receivedMsgs.size)
		Assertions.assertEquals(msg, receivedMsgs[0])
	}

	private companion object {
		val msg: Message = some(Message::class.java)
	}
}