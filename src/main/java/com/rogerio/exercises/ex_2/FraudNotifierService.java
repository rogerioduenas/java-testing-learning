package com.rogerio.exercises.ex_2;

import utils.Validate;

public class FraudNotifierService {

  private final RiskAssessmentService riskAssessmentService;
  private final FraudRepository fraudRepository;
  private final SmsService smsService;

  public FraudNotifierService(
      RiskAssessmentService riskAssessmentService,
      FraudRepository fraudRepository,
      SmsService smsService) {
    this.riskAssessmentService = riskAssessmentService;
    this.fraudRepository = fraudRepository;
    this.smsService = smsService;
  }

  public boolean analyzeAndNotify(long accountId, long transactionId, double amount) {
    Validate.positive(accountId, "AccountId must be positive");
    Validate.positive(transactionId, "TransactionId must be positive");
    Validate.positive(amount, "Amount must be greater than 0");
    int score = riskAssessmentService.getRiskScore(accountId, amount);
    if (score < 80) {
      return false;
    }
    fraudRepository.saveAlert(accountId, transactionId, score);
    smsService.sendAlert(accountId, "High risk detected");
    return true;
  }
}
