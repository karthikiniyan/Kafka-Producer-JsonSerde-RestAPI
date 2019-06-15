package com.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.producer.service.ProducerService;

@RestController
@RequestMapping
public class ProducerController {
	
	@Autowired
	private ProducerService producerService;
	@Autowired
	private Environment env;
	@GetMapping("/send")
	public String sendMessage() {
		System.out.println(env.getProperty("kafka.bootstrap.servers"));
		producerService.sendMessage();
		return "Sent Successfully";
	}

}
