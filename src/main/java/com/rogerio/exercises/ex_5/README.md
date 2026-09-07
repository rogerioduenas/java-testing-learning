### 🚀 EXERCISE 05 — Financial Report Generator with Partial Mocking (`FinancialReportGenerator`)

#### 1. 🎯 Focus & Techniques to Practice

- `@Spy` (Partial Mocking on real classes).
- **MANDATORY** use of the `doReturn(...).when(spy)...` syntax to prevent the real method from being executed during stubbing.
- Verification of the execution of internal methods within the class under test.

#### 2. 📄 Description and Business Rules

The `FinancialReportGenerator` has a public method `generateReport(long accountId)` that consolidates data by calling a heavy internal method, `fetchTaxRateFromExternalApi(long accountId)`. In unit tests, we do not want the external API to be actually called, so we use an `@Spy` on the report generator itself.

- **Business Rules:**
    - The `accountId` must be positive (`> 0`).
    - The `generateReport` method obtains the base balance by calling the `AccountBalanceService` interface.
    - It then calculates the tax by calling its own internal `fetchTaxRateFromExternalApi` method.
    - The final tax amount is `balance * taxRate`.
    - If the base balance is zero or negative, it throws `IllegalStateException` and **does NOT** call the tax rate lookup.

#### 3. 💻 Legacy Code (Copy into the IDE)


```java
public interface AccountBalanceService {
  double getBalance(long accountId);
}
```

```java
public interface AccountBalanceService {
  double getBalance(long accountId);
}
```

```java
public class FinancialReportGenerator {

  private final AccountBalanceService balanceService;

  public FinancialReportGenerator(AccountBalanceService balanceService) {
    this.balanceService = balanceService;
  }

  public double generateReport(long accountId) {
    double balance = balanceService.getBalance(accountId);
    double rate = fetchTaxRateFromExternalApi(accountId);
    return balance * rate;
  }

  public double fetchTaxRateFromExternalApi(long accountId) {
    throw new UnsupportedOperationException("Real connection to the external API is not allowed in tests!");
  }
}
```

```java
public class FinancialReportGenerator {

  private final AccountBalanceService balanceService;

  public FinancialReportGenerator(AccountBalanceService balanceService) {
    this.balanceService = balanceService;
  }

  public double generateReport(long accountId) {
    double balance = balanceService.getBalance(accountId);
    double rate = fetchTaxRateFromExternalApi(accountId);
    return balance * rate;
  }

  public double fetchTaxRateFromExternalApi(long accountId) {
    throw new UnsupportedOperationException("Real connection to the external API is not allowed in tests!");
  }
}
```