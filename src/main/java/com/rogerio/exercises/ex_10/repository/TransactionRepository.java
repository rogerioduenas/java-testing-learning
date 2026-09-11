package com.rogerio.exercises.ex_10.repository;

import com.rogerio.exercises.ex_10.model.PaymentTransaction;

public interface TransactionRepository {
  void save(PaymentTransaction transaction);
}
