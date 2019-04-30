package com.kafka.producer.kafkatfproducerservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.kafka.producer.service.ProducerService;

@Configuration
public class KafkaConfig {
	@Value("${kafka.bootstrap.servers}")
	  private String bootstrapServers;
	
	@Autowired
	private Environment env;

	  @Bean
	  public Map<String, Object> producerConfig() {
	    final Map<String, Object> props = new HashMap<>(4);

	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap.servers"));
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

	    return props;
	  }

	  @Bean
	  public ProducerFactory<Integer, String> producerFactory() {
	    return new DefaultKafkaProducerFactory<Integer, String>(producerConfig());
	  }

	  @Bean
	  public KafkaTemplate<Integer, String> kafkaTemplate() {
	    return new KafkaTemplate<Integer, String>(producerFactory());
	  }

	  @Bean
	  public ProducerService sender() {
	    return new ProducerService(kafkaTemplate());
	  }
}
