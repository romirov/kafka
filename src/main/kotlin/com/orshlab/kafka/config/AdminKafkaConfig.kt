package com.orshlab.kafka.config

import com.orshlab.kafka.config.prop.TopicsProp
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class AdminKafkaConfig(
	private val properties: KafkaProperties,
	private val topicsProp: TopicsProp
) {
	@Bean
	fun adminClient(): AdminClient {
		val props: Properties = Properties()
		props.put("bootstrap.servers", properties.bootstrapServers)
		val adminClient = AdminClient.create(props)
		val topics = topicsProp.topics
			.flatMap { (_, value) -> value }
			.map { pTopic ->
				NewTopic(pTopic, 3, 1) // 3 partitions, replication factor of 1
			}
		adminClient.createTopics(topics)
		return adminClient
	}
}