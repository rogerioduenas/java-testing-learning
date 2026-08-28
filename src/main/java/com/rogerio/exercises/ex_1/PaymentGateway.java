package com.rogerio.exercises.ex_1;

public interface PaymentGateway {
  boolean processPayment(String customerEmail, double amount);
}
