package com.rogerio.exercises.ex_10.model;

public class PaymentTransaction {

  private final long orderId;
  private final String customerEmail;
  private final double amount;
  private final String status;

  public PaymentTransaction(
      long orderId,
      String customerEmail,
      double amount,
      String status) {

    this.orderId = orderId;
    this.customerEmail = customerEmail;
    this.amount = amount;
    this.status = status;
  }

  public long getOrderId() {
    return orderId;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public double getAmount() {
    return amount;
  }

  public String getStatus() {
    return status;
  }
}
