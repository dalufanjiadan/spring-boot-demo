package com.justdoit.demo.kafka;

import com.justdoit.demo.model.User;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaConsumerService {

	@KafkaListener(topics = "${kafka.topic.test}", groupId = "${kafka.consumer.groupId.test}")
	public void consume(String message) {
		log.info(String.format("Message recieved -> %s", message));
	}

	@KafkaListener(topics = "${kafka.topic.user}", groupId = "${kafka.consumer.groupId.user}", containerFactory = "userKafkaListenerContainerFactory")
	public void consume(User user) {
		log.info(String.format("User created -> %s", user));
	}
}