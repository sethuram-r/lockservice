package smartshare.lockservice.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import smartshare.lockservice.model.S3Object;
import smartshare.lockservice.model.SagaEvent;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    private Map<String, Object> consumerConfigurationProperties = new HashMap<>();
    private Map<String, Object> producerConfigurationProperties = new HashMap<>();

    public KafkaConfiguration() {
        producerConfigurationProperties.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092" );
        producerConfigurationProperties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
        producerConfigurationProperties.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class );

        consumerConfigurationProperties.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092" );
        consumerConfigurationProperties.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true );
        consumerConfigurationProperties.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        consumerConfigurationProperties.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class );

    }

    @Bean
    public ConsumerFactory<String, S3Object[]> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>( consumerConfigurationProperties, new StringDeserializer(), new JsonDeserializer<>( S3Object[].class ) );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, S3Object[]> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, S3Object[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory( consumerFactory() );
        return factory;
    }

    @Bean
    public ProducerFactory<String, SagaEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>( producerConfigurationProperties );
    }

    @Bean
    public KafkaTemplate<String, SagaEvent> sagaEventKafkaTemplate() {
        return new KafkaTemplate<>( producerFactory() );
    }

    @Bean
    public ConsumerFactory<String, SagaEvent> sagaEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>( consumerConfigurationProperties, new StringDeserializer(), new JsonDeserializer<>( SagaEvent.class, false ) );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SagaEvent> SagaEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SagaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory( sagaEventConsumerFactory() );
        factory.setReplyTemplate( sagaEventKafkaTemplate() );
        return factory;
    }
}
