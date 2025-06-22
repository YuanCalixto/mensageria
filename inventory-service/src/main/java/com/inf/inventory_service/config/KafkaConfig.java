package com.inf.inventory_service.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
// Importe a classe Order com o mesmo PACOTE que o PRODUTOR está usando
import com.inf.order_service.model.Order; // <<< MUDANÇA AQUI!

@EnableKafka
@Configuration
public class KafkaConfig {

  // ... (producerFactory e kafkaTemplate permanecem os mesmos)

  @Bean
  // Defina o tipo genérico para a classe Order vinda do produtor
  public ConsumerFactory<String, Order> consumerFactory() { // <<< MUDANÇA AQUI!
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

    // O JsonDeserializer precisa saber qual classe desserializar,
    // seja pelo cabeçalho TypeId ou por um tipo padrão.
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.inf.order_service.model"); // Pacote da classe Order que vem no Kafka
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.inf.order_service.model.Order"); // Tipo padrão para desserialização

    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  // Defina o tipo genérico para a classe Order vinda do produtor
  public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory() { // <<< MUDANÇA AQUI!
    ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}