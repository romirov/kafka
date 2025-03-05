package com.orshlab.kafka.config.prop

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.kafka")
data class TopicsProp @ConstructorBinding constructor(
	val topics: List<String>
)