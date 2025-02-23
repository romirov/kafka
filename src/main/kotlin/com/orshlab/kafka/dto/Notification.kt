package com.orshlab.kafka.dto

data class Notification (
	val headers: Map<String, String>,
	val payload: String
)