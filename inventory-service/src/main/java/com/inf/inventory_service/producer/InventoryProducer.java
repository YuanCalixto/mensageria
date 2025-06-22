package com.inf.inventory_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inf.inventory_service.model.InventoryEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventoryProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(InventoryEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("inventory-events", event.getOrderId(), message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar InventoryEvent", e);
        }
    }
}
