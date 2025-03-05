package com.orshlab.kafka

import com.orshlab.kafka.config.prop.TopicsProp
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TopicsProp::class])
@EnableAutoConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaApplicationTests {

	@Test
	fun contextLoads() {
	}

}
