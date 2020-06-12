package com.justdoit.demo.kafka;

import com.justdoit.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaProducerService {

	// 1. General topic with a string payload

	@Value(value = "${kafka.topic.test}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	// 2. Topic with user object payload

	@Value(value = "${kafka.topic.user}")
	private String userTopicName;

	@Autowired
	private KafkaTemplate<String, User> userKafkaTemplate;

	public void sendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);

		future.addCallback((result) -> {
			log.info("Sent message: " + message + " with offset: " + result.getRecordMetadata().offset());
		}, (ex) -> {
			log.error("Unable to send message : " + message, ex);
		});
	}

	public void saveCreateUserLog(User user) {
		ListenableFuture<SendResult<String, User>> future = this.userKafkaTemplate.send(userTopicName, user);
		future.addCallback((result) -> {
			log.info("User created: " + user + " with offset: " + result.getRecordMetadata().offset());
		}, (ex) -> {
			log.error("User created : " + user, ex);
		});
	}
}