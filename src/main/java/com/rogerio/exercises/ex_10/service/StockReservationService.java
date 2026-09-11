package com.rogerio.exercises.ex_10.service;

public interface StockReservationService {
  void reserve(
      String productId,
      int quantity,
      long orderId);
}
