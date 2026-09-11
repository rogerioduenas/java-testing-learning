package com.rogerio.exercises.ex_10.exception;

public class StockReservationException extends RuntimeException {
  public StockReservationException(String message) {
    super(message);
  }
}
