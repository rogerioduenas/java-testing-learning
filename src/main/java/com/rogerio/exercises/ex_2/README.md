## 🚀 EXERCISE 02 — Account Fraud Notifier (`FraudNotifierService`)

---

#### 1. 🎯 Focus & Techniques to Practice

- BDD configuration with exceptions: `given(...).willThrow(...)`
- Verification of null/absent interactions: `verifyNoInteractions(...)` or `shouldHaveNoInteractions()`
- Resilience testing and exception propagation

#### 2. 📄 Exercise Description & Business Rules

The `FraudNotifierService` queries a risk assessment service (`RiskAssessmentService`). If a transaction is considered suspicious (risk score `>= 80`), the system must record the fraud in the repository (`FraudRepository`) and notify the security team via `SmsService`.

- **Business Rules:**
    - The account ID (`accountId`) and transaction ID (`transactionId`) must be positive (`> 0`).
    - The transaction amount must be strictly greater than zero (`> 0`).
    - If the risk score is `< 80`, the transaction is safe: **DO NOT** save it to the repository, **DO NOT** send an SMS, and return `false`.
    - If the risk score is `>= 80`, save the fraud to the repository, send an SMS, and return `true`.
    - If the `RiskAssessmentService` fails (throws an exception), the service must propagate the exception and **MUST NOT** interact with the repository or the SMS service.

#### 3. 💻 Legacy Code (Copy into Your IDE)

```java
public interface RiskAssessmentService {
  int getRiskScore(long accountId, double amount);
}
```

```java
public interface FraudRepository {
  void saveAlert(long accountId, long transactionId, int riskScore);
}
```

```java
public interface SmsService {
  void sendAlert(long accountId, String message);
}
```

```java
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
    // LEGACY: Does not validate inputs and notifies regardless of the risk level!
    int score = riskAssessmentService.getRiskScore(accountId, amount);
    fraudRepository.saveAlert(accountId, transactionId, score);
    smsService.sendAlert(accountId, "High risk detected");
    return true;
  }
}
```
