package com.rogerio.exercises.ex_10.model;

public class PaymentResult {

  private final long orderId;
  private final String status;

  public PaymentResult(long orderId, String status) {
    this.orderId = orderId;
    this.status = status;
  }

  public long getOrderId() {
    return orderId;
  }

  public String getStatus() {
    return status;
  }
}
