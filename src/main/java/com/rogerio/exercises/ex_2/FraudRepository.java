package com.rogerio.exercises.ex_2;

public interface FraudRepository {
  void saveAlert(long accountId, long transactionId, int riskScore);
}
