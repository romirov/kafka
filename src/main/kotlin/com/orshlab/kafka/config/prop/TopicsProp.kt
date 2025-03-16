package com.orshlab.kafka.config.prop

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.kafka")
data class TopicsProp @ConstructorBinding constructor(
	val topics: Map<TopicOwner, List<String>>
)

enum class TopicOwner(value: String) {
	PRODUCER("producer"),
	CONSUMER("consumer"),
	TABLE("table")
}