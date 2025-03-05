package com.orshlab.kafka.dto

data class KafkaMessage (
	val headers: Map<String, String>,
	val payload: String
)