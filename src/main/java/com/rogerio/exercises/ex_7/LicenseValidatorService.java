package com.rogerio.exercises.ex_7;

import utils.Validate;

import java.time.LocalDateTime;

public class LicenseValidatorService {

  public boolean isLicenseValid(License license) {
    Validate.notNull(license, "Licence must not be null");

    LocalDateTime now = LocalDateTime.now();

    return !now.isAfter(license.getExpirationDate());
  }
}
