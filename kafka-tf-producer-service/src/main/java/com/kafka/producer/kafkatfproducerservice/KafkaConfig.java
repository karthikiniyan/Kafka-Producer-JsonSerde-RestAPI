package com.kafka.producer.kafkatfproducerservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.kafka.producer.service.ProducerService;
import com.kafka.producer.service.Weather;

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
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Weather.class);
	    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

	    return props;
	  }

	  @Bean
	  public ProducerFactory<String, Weather> producerFactory() {
	    return new DefaultKafkaProducerFactory<String, Weather>(producerConfig());
	  }

	  @Bean
	  public KafkaTemplate<String, Weather> kafkaTemplate() {
	    return new KafkaTemplate<String, Weather>(producerFactory());
	  }

	  @Bean
	  public ProducerService sender() {
	    return new ProducerService(kafkaTemplate());
	  }
}
