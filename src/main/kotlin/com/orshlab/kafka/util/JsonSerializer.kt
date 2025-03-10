package com.orshlab.kafka.util

import com.orshlab.kafka.dto.Message
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.serializer

object JsonSerializer {
	@OptIn(InternalSerializationApi::class)
	private val json = Json {
		serializersModule = SerializersModule{
			contextual(UUIDSerializer)
			contextual(LocalDateTimeSerializer)
		}
	}

	@OptIn(InternalSerializationApi::class)
	fun serializeToString(msg: Message): String = json.encodeToString(Message::class.serializer(), msg)

	@OptIn(InternalSerializationApi::class)
	fun deserializeFromString(msg: String): Message = json.decodeFromString(Message::class.serializer(), msg)
}