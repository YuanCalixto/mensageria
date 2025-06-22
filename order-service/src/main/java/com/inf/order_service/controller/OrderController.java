package com.inf.order_service.controller;

import com.inf.order_service.model.Order;
import com.inf.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<String> createOrder(@RequestBody Order order) {
    orderService.processOrder(order);
    return ResponseEntity.ok("Pedido enviado com sucesso!");
  }
}
