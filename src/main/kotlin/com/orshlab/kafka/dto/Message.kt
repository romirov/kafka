package com.orshlab.kafka.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class Message (
	@Contextual
	val id: UUID = UUID.randomUUID(),
	val title: String,
	val content: String,
	val author: String,
	@Contextual
	val createdTime: LocalDateTime = LocalDateTime.now()
)

