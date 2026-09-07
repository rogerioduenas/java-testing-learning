package com.rogerio.exercises.ex_5;

import utils.Validate;

public class FinancialReportGenerator {

  private final AccountBalanceService balanceService;

  public FinancialReportGenerator(AccountBalanceService balanceService) {
    this.balanceService = balanceService;
  }

  public double generateReport(long accountId) {
    Validate.positive(accountId, "Account ID must be positive");

    double balance = balanceService.getBalance(accountId);
    if (balance <= 0) {
      throw new IllegalStateException("Balance must be positive");
    }

    double rate = fetchTaxRateFromExternalApi(accountId);

    return balance * rate;
  }

  public double fetchTaxRateFromExternalApi(long accountId) {
    Validate.positive(accountId, "Account ID must be positive");
    throw new UnsupportedOperationException("Actual connection to the external API is not allowed in tests!");
  }
}
