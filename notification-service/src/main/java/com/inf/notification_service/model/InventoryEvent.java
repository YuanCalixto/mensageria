package com.inf.notification_service.model;

public class InventoryEvent {

  private String orderId;
  private boolean success;
  private String message;
  private Double processedAt;

  public String getOrderId() {
    return orderId;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }

  public Double getProcessedAt() {
    return processedAt;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setProcessedAt(Double processedAt) {
    this.processedAt = processedAt;
  }
}
