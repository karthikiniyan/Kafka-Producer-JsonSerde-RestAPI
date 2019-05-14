package com.kafka.producer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
		String topic = "meenatopic";

		File file = new File("C:/KafkaProd/");
		File[] files = file.listFiles();
		for (File f : files) {
			System.out.println(f.getName());
			String fileName = f.getName();
			FileInputStream fis;
			try {

				fis = new FileInputStream("C:/KafkaProd/" + fileName);
				byte[] filevalue = new byte[(int) file.length()];
				fis.read(filevalue);
				fis.close();

				String filecontent = new String(filevalue, "UTF-8");

				System.out.println(filecontent);

				{
					System.out.println("message : " + filecontent);
					kafkaTemplate.send(topic, filecontent)
							.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

								@Override
								public void onSuccess(final SendResult<Integer, String> result) {
									LOGGER.info("sent message='{}' with offset={}", filecontent,
											result.getRecordMetadata().offset());
								}

								@Override
								public void onFailure(final Throwable exc) {
									LOGGER.error("unable to send message='{}'", filecontent, exc);
								}
							});
				}

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

	}

}
