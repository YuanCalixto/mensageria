package com.inf.order_service.producer;

import com.inf.order_service.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

  private final KafkaTemplate<String, Order> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(Order order) {
    kafkaTemplate.send("orders", order.getOrderId(), order);
  }
}
