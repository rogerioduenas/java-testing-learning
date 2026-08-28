package com.rogerio.exercises.ex_1;

import utils.Validate;

public class OrderProcessor {

  private final PaymentGateway paymentGateway;
  private final EmailService emailService;

  public OrderProcessor(PaymentGateway paymentGateway, EmailService emailService) {
    this.paymentGateway = paymentGateway;
    this.emailService = emailService;
  }

  public boolean processOrder(String customerEmail, double amount) {
    Validate.notBlank(customerEmail, "Customer email cannot be blank or null");
    Validate.positive(amount, "Amount must be positive");

    boolean paid = paymentGateway.processPayment(customerEmail, amount);

    if (paid) {
      emailService.sendConfirmation(customerEmail, amount);
    }

    return paid;
  }
}
