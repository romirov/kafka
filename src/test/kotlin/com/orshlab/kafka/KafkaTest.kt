package com.orshlab.kafka

import com.ocadotechnology.gembus.test.Arranger.some
import com.orshlab.kafka.dto.Message
import com.orshlab.kafka.service.ConsumerService
import com.orshlab.kafka.service.ProducerService
import org.apache.kafka.streams.kstream.KTable
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration

class KafkaTest: AbstractKafkaTest() {

	@Autowired
	lateinit var consumer: ConsumerService

	@Autowired
	lateinit var producer: ProducerService

	@Autowired
	lateinit var kTable: KTable<Int, String>

	@Test
	fun test() {
		logger.info("PRODUCER MESSAGE: ${consumer.getMessage()}")
		producer.send(msg, 1)
		Thread.sleep(Duration.ofSeconds(5))
		kTable.mapValues { string -> logger.info("TABLE MESSAGE: $string") }
		val receivedMsgs = consumer.getMessage()
		receivedMsgs.forEach { message ->
			logger.info("CONSUMER MESSAGE: ${consumer.getMessage()}")
		}
		Assertions.assertEquals(1, receivedMsgs.size)
		Assertions.assertEquals(msg, receivedMsgs[0])
	}

	private companion object {
		val msg: Message = some(Message::class.java)
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}