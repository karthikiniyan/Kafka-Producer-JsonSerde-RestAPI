package com.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

	private final KafkaTemplate<String, Weather> kafkaTemplate;

	@Autowired
	public ProducerService(KafkaTemplate<String,Weather> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage() {
		String topic = "Streams_input";
		
	
		for (Weather weather : CountryEnum.getCountryList()) {

			try {
				Thread.sleep(2000);
				System.out.println("sending data='{}' to topic='{}'" + weather.toString() + "" + topic);
				Message<Weather> message = MessageBuilder.withPayload(weather).setHeader(KafkaHeaders.TOPIC, topic)
						.setHeader(KafkaHeaders.MESSAGE_KEY, weather.getCountry()).build();
				
				this.kafkaTemplate.send(message);

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

	}

}
