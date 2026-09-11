package com.rogerio.exercises.ex_10.exception;

public class PaymentRejectedException extends RuntimeException {
  public PaymentRejectedException(String message) {
    super(message);
  }
}
