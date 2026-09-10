package com.rogerio.exercises.ex_9;

import java.time.LocalDateTime;

public class Coupon {
  private final String code;
  private final double discountPercentage;
  private final LocalDateTime expirationDate;

  public Coupon(String code, double discountPercentage, LocalDateTime expirationDate) {
    this.code = code;
    this.discountPercentage = discountPercentage;
    this.expirationDate = expirationDate;
  }

  public String getCode() { return code; }
  public double getDiscountPercentage() { return discountPercentage; }
  public LocalDateTime getExpirationDate() { return expirationDate; }
}
