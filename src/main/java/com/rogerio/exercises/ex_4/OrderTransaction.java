package com.rogerio.exercises.ex_4;

import utils.Validate;

public class OrderTransaction {
  private final String customerEmail;
  private final double originalAmount;
  private final double netAmount;
  private final String status;

  public OrderTransaction(String customerEmail, double originalAmount, double netAmount, String status) {
    validateInputs(customerEmail, originalAmount, netAmount, status);
    this.customerEmail = customerEmail;
    this.originalAmount = originalAmount;
    this.netAmount = netAmount;
    this.status = status;
  }

  public String getCustomerEmail() { return customerEmail; }
  public double getOriginalAmount() { return originalAmount; }
  public double getNetAmount() { return netAmount; }
  public String getStatus() { return status; }

  private void validateInputs(String customerEmail, double originalAmount, double netAmount, String status) {
    Validate.notBlank(customerEmail, "Customer email is required");
    Validate.positive(originalAmount, "Original amount must be positive");
    Validate.positive(netAmount, "Net amount must be positive");
    Validate.notBlank(status, "Status is required");
  }
}
