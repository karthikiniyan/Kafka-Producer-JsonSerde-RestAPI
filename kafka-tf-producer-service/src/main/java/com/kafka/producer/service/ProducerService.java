package com.kafka.producer.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

	private final KafkaTemplate<Integer, String> kafkaTemplate;

	@Autowired
	public ProducerService(final KafkaTemplate<Integer, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage() {
		String topic ="test1";

		try {

			File f = new File("src/main/resources/filestoPost/postKafkaTopicLineByLine");

			List<String> lines = FileUtils.readLines(f, "UTF-8");

			for (String message : lines) {
				System.out.println("message : " + message);
				kafkaTemplate.send(topic, message)
						.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

							@Override
							public void onSuccess(final SendResult<Integer, String> result) {
								LOGGER.info("sent message='{}' with offset={}", message,
										result.getRecordMetadata().offset());
							}

							@Override
							public void onFailure(final Throwable exc) {
								LOGGER.error("unable to send message='{}'", message, exc);
							}
						});
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
