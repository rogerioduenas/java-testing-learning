package com.rogerio.exercises.ex_3;

import utils.Validate;

public class TransferService {

  private final AccountRepository accountRepository;
  private final AuditLogger auditLogger;

  public TransferService(AccountRepository accountRepository, AuditLogger auditLogger) {
    this.accountRepository = accountRepository;
    this.auditLogger = auditLogger;
  }

  public void executeTransfer(long fromAccountId, long toAccountId, double amount) {
    Validate.positive(fromAccountId, "From account id must be positive");
    Validate.positive(toAccountId, "To account id must be positive");

    if (fromAccountId == toAccountId) {
      throw new IllegalArgumentException("Source and destination accounts must be different");
    }

    Account from = accountRepository.findById(fromAccountId);
    Account to = accountRepository.findById(toAccountId);

    from.debit(amount);
    to.credit(amount);

    accountRepository.update(from);
    accountRepository.update(to);
    auditLogger.logTransfer(fromAccountId, toAccountId, amount);
  }
}
