package com.inf.order_service.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {

  private String orderId;
  private Instant timestamp;
  private List<String> items;

  public Order() {
    this.orderId = UUID.randomUUID().toString();
    this.timestamp = Instant.now();
  }

  public String getOrderId() {
    return orderId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public List<String> getItems() {
    return items;
  }

  public void setItems(List<String> items) {
    this.items = items;
  }
}
