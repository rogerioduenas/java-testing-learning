package com.rogerio.exercises.ex_10.service;

import com.rogerio.exercises.ex_10.model.PaymentResult;

public interface PaymentGateway {
  PaymentResult processPayment(
      long orderId,
      String customerEmail,
      double amount);
}
