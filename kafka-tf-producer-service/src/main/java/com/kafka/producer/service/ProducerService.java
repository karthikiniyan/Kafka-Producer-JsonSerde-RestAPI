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
	
	@Autowired
	private WeatherReportUtil weather;

	public void sendMessage() {
		
		String topic = "Streams_input";
			
		try {
		for (String city : CityConstant.CITY) {
			Weather wea=weather.getWeatherReport(city.trim());
			//Thread.sleep(1000);
			if(wea != null) {
			System.out.println("sending data='{}' to topic='{}'" + wea.toString() + "" + topic);
				Message<Weather> message = MessageBuilder.withPayload(wea).setHeader(KafkaHeaders.TOPIC, topic)
						.setHeader(KafkaHeaders.MESSAGE_KEY, wea.getCity()).build();
				this.kafkaTemplate.send(message);
			}
			} 
		}
		catch (Exception e) {

				e.printStackTrace();

			}
		}

	}


