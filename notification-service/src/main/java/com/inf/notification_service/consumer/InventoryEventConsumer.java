package com.inf.notification_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inf.notification_service.model.InventoryEvent;
import com.inf.notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

  private final ObjectMapper objectMapper;
  private final NotificationService notificationService;

  public InventoryEventConsumer(NotificationService notificationService) {
    this.notificationService = notificationService;
    this.objectMapper = new ObjectMapper();
  }

  @KafkaListener(topics = "inventory-events", groupId = "notification-group")
  public void consume(String message) {
    try {
      InventoryEvent event = objectMapper.readValue(message, InventoryEvent.class);
      notificationService.notifyUser(event);
    } catch (Exception e) {
      System.err.println("Erro ao processar evento de inventário: " + e.getMessage());
    }
  }
}
