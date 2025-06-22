package com.inf.inventory_service.consumer;

import com.inf.inventory_service.service.InventoryService;
import com.inf.order_service.model.Order;
import java.time.Instant;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

  private final InventoryService inventoryService;

  public OrderConsumer(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @KafkaListener(topics = "orders", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactory")
  // Change the parameter type from Map<String, Object> to Order
  public void consume(Order order) { // <<< MUDANÇA AQUI
    System.out.println("Recebido pedido: " + order.getOrderId());

    // Agora você pode acessar os campos diretamente do objeto Order
    String orderId = order.getOrderId();
    List<String> items = order.getItems();
    Instant timestamp = order.getTimestamp();

    inventoryService.process(orderId, items, timestamp);
  }

}