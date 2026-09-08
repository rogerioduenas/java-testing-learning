package com.rogerio.exercises.ex_7;

import utils.Validate;

import java.time.LocalDateTime;

public class License {
  private final String productCode;
  private final LocalDateTime expirationDate;

  public License(String productCode, LocalDateTime expirationDate) {
    Validate.notBlank(productCode, "Product code must not be blank");
    Validate.notNull(expirationDate, "Expiration date must not be null");
    this.productCode = productCode;
    this.expirationDate = expirationDate;
  }

  public String getProductCode() { return productCode; }
  public LocalDateTime getExpirationDate() { return expirationDate; }
}
