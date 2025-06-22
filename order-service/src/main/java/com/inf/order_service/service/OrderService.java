package com.inf.order_service.service;

import com.inf.order_service.model.Order;
import com.inf.order_service.producer.OrderProducer;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderProducer orderProducer;

  public OrderService(OrderProducer orderProducer) {
    this.orderProducer = orderProducer;
  }

  public void processOrder(Order order) {
    orderProducer.send(order);
  }
}
