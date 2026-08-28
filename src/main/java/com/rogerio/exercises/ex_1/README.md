## 🚀 EXERCISE 01 — Order Processor (`OrderProcessor`)

#### 1. 🎯 Focus & Techniques to Practice

- `@Mock`, `@InjectMocks`, `@ExtendWith(MockitoExtension.class)`
- BDD configuration: `given(...).willReturn(...)`
- BDD verification: `then(...).should(...)` / `then(...).shouldHaveNoInteractions()`
- `@ParameterizedTest` + `assertThrows` for domain exceptions

#### 2. 📄 Exercise Description and Business Rules

The `OrderProcessor` must process an order by charging the customer through the `PaymentGateway` and, **only if the payment is successful**, send a confirmation email through the `EmailService`.

- **Business Rules:**
    - The customer's email must not be null or blank.
    - The order amount (`amount`) must be greater than zero (`> 0`).
    - If the payment is approved by the gateway, send the confirmation email and return `true`.
    - If the payment is declined by the gateway, **DO NOT** send the email and return `false`.
    - If the input data is invalid, throw `IllegalArgumentException` and **DO NOT** call the gateway or the email service.

#### 3. 💻 Legacy Code (Copy into Your IDE)

Java

```java
public interface PaymentGateway {
  boolean processPayment(String customerEmail, double amount);
}
```

```java
public interface PaymentGateway {
  boolean processPayment(String customerEmail, double amount);
}
```

```java
public interface EmailService {
  void sendConfirmation(String customerEmail, double amount);
}
```

```java
public interface EmailService {
  void sendConfirmation(String customerEmail, double amount);
}
```

```java
public class OrderProcessor {

  private final PaymentGateway paymentGateway;
  private final EmailService emailService;

  public OrderProcessor(PaymentGateway paymentGateway, EmailService emailService) {
    this.paymentGateway = paymentGateway;
    this.emailService = emailService;
  }

  public boolean processOrder(String customerEmail, double amount) {
    // LEGACY: Processes blindly without validating data and sends an email even if the payment fails!
    boolean paid = paymentGateway.processPayment(customerEmail, amount);
    emailService.sendConfirmation(customerEmail, amount);
    return paid;
  }
}
```

```java
public class OrderProcessor {

  private final PaymentGateway paymentGateway;
  private final EmailService emailService;

  public OrderProcessor(PaymentGateway paymentGateway, EmailService emailService) {
    this.paymentGateway = paymentGateway;
    this.emailService = emailService;
  }

  public boolean processOrder(String customerEmail, double amount) {
    // LEGACY: Processes blindly without validating data and sends an email even if the payment fails!
    boolean paid = paymentGateway.processPayment(customerEmail, amount);
    emailService.sendConfirmation(customerEmail, amount);
    return paid;
  }
}
``` 
