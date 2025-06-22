package com.inf.notification_service.service;

import com.inf.notification_service.model.InventoryEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyUser(InventoryEvent event) {
        System.out.println("[Notificação] Pedido " + event.getOrderId() +
                (event.isSuccess() ? " confirmado!" : " falhou: " + event.getMessage()));
    }
}
