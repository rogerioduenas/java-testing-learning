## 🚀 EXERCISE 03 — Bank Transfer Processor (`TransferService`)

---

#### 1. 🎯 Focus & Techniques to Practice

- Verifying strict call order (`InOrder`)
- Mocking multiple dependencies simultaneously (`AccountRepository`, `AuditLogger`)
- Simulating persistence failure and behavioral rollback

#### 2. 📄 Statement and Business Rules

The `TransferService` performs transfers between two bank accounts. To ensure traceability, database operations and audit logging must follow a strict execution order.

- **Business Rules:**
    - The source account and destination account cannot be the same or have IDs `<= 0`.
    - The transfer amount must be greater than zero (`> 0`).
    - The source account balance must be sufficient (`balance >= amount`). If insufficient, it throws `IllegalStateException` and **MUST NOT** modify balances or call the repository.
    - **Strict Execution Order on Success:**
        - 1st: Debit the source account.
        - 2nd: Credit the destination account.
        - 3rd: Update the source account in the repository.
        - 4th: Update the destination account in the repository.
        - 5th: Record the transfer in the `AuditLogger`.

#### 3. 💻 Legacy Code (Copy into the IDE)

```java
public class Account {
  private final long id;
  private double balance;

  public Account(long id, double balance) {
    this.id = id;
    this.balance = balance;
  }

  public long getId() { return id; }
  public double getBalance() { return balance; }
  public void debit(double amount) { this.balance -= amount; }
  public void credit(double amount) { this.balance += amount; }
}
```

```java
public interface AccountRepository {
  Account findById(long id);
  void update(Account account);
}
```

```java
public interface AuditLogger {
  void logTransfer(long fromId, long toId, double amount);
}
```

```java
public class TransferService {

  private final AccountRepository accountRepository;
  private final AuditLogger auditLogger;

  public TransferService(AccountRepository accountRepository, AuditLogger auditLogger) {
    this.accountRepository = accountRepository;
    this.auditLogger = auditLogger;
  }

  public void executeTransfer(long fromAccountId, long toAccountId, double amount) {
    Account from = accountRepository.findById(fromAccountId);
    Account to = accountRepository.findById(toAccountId);

    from.debit(amount);
    to.credit(amount);

    accountRepository.update(from);
    accountRepository.update(to);
    auditLogger.logTransfer(fromAccountId, toAccountId, amount);
  }
}
```
