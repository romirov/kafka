package com.orshlab.kafka.dto

import java.time.LocalDateTime
import java.util.*

data class Message (
	val id: UUID = UUID.randomUUID(),
	val title: String,
	val content: String,
	val author: String,
	val createdTime: LocalDateTime = LocalDateTime.now()
)