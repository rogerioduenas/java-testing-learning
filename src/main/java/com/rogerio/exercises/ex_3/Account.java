package com.rogerio.exercises.ex_3;

import utils.Validate;

public class Account {
  private final long id;
  private double balance;

  public Account(long id, double balance) {
    Validate.positive(id, "Account ID can't be negative");
    Validate.notNegative(balance, "Balance can't be negative");
    this.id = id;
    this.balance = balance;
  }

  public long getId() { return id; }
  public double getBalance() { return balance; }

  public void debit(double amount) {
    Validate.positive(amount, "Amount must be positive");
    if (amount > balance) {
      throw new IllegalStateException("Insufficient funds");
    }
    this.balance -= amount;
  }

  public void credit(double amount) {
    Validate.positive(amount, "Amount must be positive");
    this.balance += amount;
  }
}
