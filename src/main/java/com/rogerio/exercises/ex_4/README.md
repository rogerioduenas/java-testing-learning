## 🚀 EXERCISE 04 — Order Processor with DTO Transformation

## (`OrderProcessingService`)

---

#### 1. 🎯 Focus & Techniques to Practice

- `ArgumentCaptor` (or `@Captor`) to capture and inspect complex objects created internally.
- Verification of values and attributes encapsulated within the captured object.
- `@ParameterizedTest` + `assertThrows` for defensive protections.

#### 2. 📄 Exercise Description and Business Rules

The `OrderProcessingService` receives sales data, converts this simple data into a complex `OrderTransaction` object, and sends it to the `TransactionRepository`.

- **Business Rules:**
    - The customer's email cannot be null or blank. The order amount must be greater than zero (`> 0`).
    - When processing, the service must instantiate an `OrderTransaction` with:
        - `customerEmail`: the provided email.
        - `originalAmount`: the original order amount.
        - `netAmount`: the amount with a 10% discount (if the amount is `>= 100.0`) or without a discount (if it is `< 100.0`).
        - `status`: String with the value `"PROCESSED"`.
    - The test **MUST use** **`ArgumentCaptor<OrderTransaction>`** to capture the object passed to `repository.save(...)` and validate whether the discount and status were calculated/assigned correctly.

#### 3. 💻 Legacy Code (Copy into the IDE)

```java

public class OrderTransaction {
  private final String customerEmail;
  private final double originalAmount;
  private final double netAmount;
  private final String status;

  public OrderTransaction(String customerEmail, double originalAmount, double netAmount, String status) {
    this.customerEmail = customerEmail;
    this.originalAmount = originalAmount;
    this.netAmount = netAmount;
    this.status = status;
  }

  public String getCustomerEmail() { return customerEmail; }
  public double getOriginalAmount() { return originalAmount; }
  public double getNetAmount() { return netAmount; }
  public String getStatus() { return status; }
}
```

```java

public class OrderTransaction {
  private final String customerEmail;
  private final double originalAmount;
  private final double netAmount;
  private final String status;

  public OrderTransaction(String customerEmail, double originalAmount, double netAmount, String status) {
    this.customerEmail = customerEmail;
    this.originalAmount = originalAmount;
    this.netAmount = netAmount;
    this.status = status;
  }

  public String getCustomerEmail() { return customerEmail; }
  public double getOriginalAmount() { return originalAmount; }
  public double getNetAmount() { return netAmount; }
  public String getStatus() { return status; }
}
```

```java

public interface TransactionRepository {
  void save(OrderTransaction transaction);
}
```

```java

public interface TransactionRepository {
  void save(OrderTransaction transaction);
}
```

```java

public class OrderProcessingService {

  private final TransactionRepository repository;

  public OrderProcessingService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void process(String customerEmail, double amount) {
    // LEGACY: Does not validate data and applies a fixed incorrect discount without considering the threshold!
    double netAmount = amount * 0.90; 
    OrderTransaction tx = new OrderTransaction(customerEmail, amount, netAmount, "PROCESSED");
    repository.save(tx);
  }
}
```

```java

public class OrderProcessingService {

  private final TransactionRepository repository;

  public OrderProcessingService(TransactionRepository repository) {
    this.repository = repository;
  }

  public void process(String customerEmail, double amount) {
    // LEGACY: Does not validate data and applies a fixed incorrect discount without considering the threshold!
    double netAmount = amount * 0.90; 
    OrderTransaction tx = new OrderTransaction(customerEmail, amount, netAmount, "PROCESSED");
    repository.save(tx);
  }
}
```