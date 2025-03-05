package com.orshlab.kafka.util

import com.orshlab.kafka.dto.Message
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import kotlinx.serialization.serializer
import java.time.LocalDateTime
import java.util.*

object JsonSerializer {
	@OptIn(InternalSerializationApi::class)
	private val json = Json {
		serializersModuleOf(LocalDateTime::class.serializer())
		serializersModuleOf(UUID::class.serializer())
	}

	@OptIn(InternalSerializationApi::class)
	fun serializeToString(msg: Message): String = json.encodeToString(Message::class.serializer(), msg)

	@OptIn(InternalSerializationApi::class)
	fun deserializeFromString(msg: String): Message = json.decodeFromString(Message::class.serializer(), msg)
}