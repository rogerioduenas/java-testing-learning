package com.rogerio.exercises.ex_4;

import utils.Validate;

public class OrderProcessingService {

  private final TransactionRepository repository;

  public OrderProcessingService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void process(String customerEmail, double amount) {
    validateInputs(customerEmail, amount);
    String status = "PROCESSED";
    double netAmount = (amount >= 100.0) ? amount * 0.90 : amount;

    OrderTransaction orderTransaction = new OrderTransaction(
        customerEmail,
        amount,
        netAmount,
        status);

    repository.save(orderTransaction);
  }

  private void validateInputs(String customerEmail, double amount) {
    Validate.notBlank(customerEmail, "Customer email cannot be blank");
    Validate.positive(amount, "Amount must be positive");
  }
}
