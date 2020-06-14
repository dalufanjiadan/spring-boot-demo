package com.justdoit.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.justdoit.demo.model.ChartMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChartController {
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public ChartMessage send(ChartMessage message) throws Exception {
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new ChartMessage(message.getFrom(), message.getText(), time);
	}
}