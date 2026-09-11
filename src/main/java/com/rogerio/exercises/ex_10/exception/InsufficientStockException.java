package com.rogerio.exercises.ex_10.exception;

public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(String message) {
    super(message);
  }
}
