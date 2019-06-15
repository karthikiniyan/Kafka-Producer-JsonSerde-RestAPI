package com.kafka.producer.kafkatfproducerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.kafka.producer")
@EnableEurekaClient
public class KafkaTfProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTfProducerServiceApplication.class, args);
		
	}

}
