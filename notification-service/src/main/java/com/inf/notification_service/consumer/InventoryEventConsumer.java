package com.inf.notification_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inf.notification_service.model.InventoryEvent;
import com.inf.notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

  private final ObjectMapper objectMapper;
  private final NotificationService notificationService;
  private final SimpMessagingTemplate messagingTemplate;

  public InventoryEventConsumer(NotificationService notificationService, ObjectMapper objectMapper, SimpMessagingTemplate messagingTemplate) {
    this.notificationService = notificationService;
    this.objectMapper = objectMapper;
    this.messagingTemplate = messagingTemplate;
  }

  @KafkaListener(topics = "inventory-events", groupId = "notification-group")
  public void consume(String message) {
    try {
      System.out.println("Recebeu mensagem do Kafka: " + message);
      InventoryEvent event = objectMapper.readValue(message, InventoryEvent.class);
      notificationService.notifyUser(event);

      // envia via WebSocket para os clientes conectados
      messagingTemplate.convertAndSend("/topic/notificacoes", message);
    } catch (Exception e) {
      System.err.println("Erro ao processar evento de inventário: " + e.getMessage());
    }
  }

}
