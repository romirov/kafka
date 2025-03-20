package com.orshlab.kafka.config

import com.orshlab.kafka.config.prop.TopicOwner
import com.orshlab.kafka.config.prop.TopicsProp
import com.orshlab.kafka.util.JsonSerializer
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.*
import org.apache.kafka.streams.processor.WallclockTimestampExtractor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.config.StreamsBuilderFactoryBean
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer


@Configuration
@EnableKafkaStreams
class StreamKafkaConfig(
	private val properties: KafkaProperties,
	private val topicsProp: TopicsProp
) {
	@Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
	fun streamProps(): KafkaStreamsConfiguration = KafkaStreamsConfiguration(
		mapOf(
			StreamsConfig.APPLICATION_ID_CONFIG to properties.streams.applicationId,
			StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
			StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.Integer()::class.java,
			StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String()::class.java,
			StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG to WallclockTimestampExtractor::class.java
		)
	)

	@Bean
	fun configurer(): StreamsBuilderFactoryBeanConfigurer {
		return StreamsBuilderFactoryBeanConfigurer { fb: StreamsBuilderFactoryBean ->
			fb.setStateListener { newState: KafkaStreams.State?, oldState: KafkaStreams.State? ->
				logger.info("State transition from $oldState to $newState")
			}
		}
	}

	@Bean
	fun kStream(kStreamBuilder: StreamsBuilder): KStream<Int, String> {
		val stream = kStreamBuilder.stream(
			topicsProp.topics[TopicOwner.PRODUCER]?.single(),
			Consumed.with(Serdes.Integer(), Serdes.String())
		)

		stream.mapValues(
			ValueMapper { obj: String ->
				logger.info("STREAM MESSAGE: $obj, TOPIC: ${topicsProp.topics[TopicOwner.PRODUCER]}")
				val message = JsonSerializer.deserializeFromString(obj)
				val updMsg = message.copy(author = "Heinrich Heine")
				JsonSerializer.serializeToString(updMsg)
			}
		).to(
			topicsProp.topics[TopicOwner.CONSUMER]?.single(),
			Produced.with(Serdes.Integer(), Serdes.String())
		)

		stream.print(Printed.toSysOut())

		return stream
	}

//	@Bean
//	fun kTable(kStream: KStream<Int, String>): KTable<Int, String> {
//		val table = kStream.mapValues { textLine ->
//			"TABLE MESSAGE: ${textLine.lowercase().split("\\W+")}"
//		}.toTable(Named.`as`(topicsProp.topics[TopicOwner.TABLE]?.single()))
//		return table
//	}

	private companion object {
		val logger: Logger = LoggerFactory.getLogger(this::class.java)
	}
}