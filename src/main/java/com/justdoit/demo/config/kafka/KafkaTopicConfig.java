package com.justdoit.demo.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topic.test}")
	private String topicNameTest;

	@Value(value = "${kafka.topic.user}")
	private String topicNameUser;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic generalTopic() {
		return TopicBuilder//
				.name(topicNameTest)//
				.partitions(1)//
				.replicas(1)//
				.build();
	}

	@Bean
	public NewTopic userTopic() {
		return TopicBuilder//
				.name(topicNameUser)//
				.partitions(1)//
				.replicas(1)//
				.build();
	}
}