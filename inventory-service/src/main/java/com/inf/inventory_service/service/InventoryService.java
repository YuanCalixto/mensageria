package com.inf.inventory_service.service;

import com.inf.inventory_service.model.InventoryEvent;
import com.inf.inventory_service.producer.InventoryProducer;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

  private final InventoryProducer inventoryProducer;
  private final Random random = new Random();

  public InventoryService(InventoryProducer inventoryProducer) {
    this.inventoryProducer = inventoryProducer;
  }

  public void process(String orderId, List<String> items, Instant timestamp) {
    InventoryEvent event = new InventoryEvent();
    event.setOrderId(orderId);
    event.setProcessedAt(Instant.now());

    boolean success = new Random().nextBoolean(); // Simula se tem estoque ou não

    if (success) {
      event.setSuccess(true);
      event.setMessage("Estoque reservado com sucesso.");
    } else {
      event.setSuccess(false);
      event.setMessage("Estoque insuficiente.");
    }

    inventoryProducer.send(event);
  }

}
