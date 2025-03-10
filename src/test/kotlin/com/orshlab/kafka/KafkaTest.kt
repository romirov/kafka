package com.orshlab.kafka

import com.ocadotechnology.gembus.test.Arranger.some
import com.orshlab.kafka.AbstractKafkaTest
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.service.Consumer
import com.orshlab.kafka.service.Producer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KafkaTest: AbstractKafkaTest() {

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