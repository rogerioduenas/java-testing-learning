package com.rogerio.exercises.ex_9;

import java.time.LocalDateTime;

public class CheckoutOrder {
  private final String customerEmail;
  private final double finalAmount;
  private final LocalDateTime createdAt;
  private final String status;

  public CheckoutOrder(String customerEmail, double finalAmount, LocalDateTime createdAt, String status) {
    this.customerEmail = customerEmail;
    this.finalAmount = finalAmount;
    this.createdAt = createdAt;
    this.status = status;
  }

  public String getCustomerEmail() { return customerEmail; }
  public double getFinalAmount() { return finalAmount; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public String getStatus() { return status; }
}
