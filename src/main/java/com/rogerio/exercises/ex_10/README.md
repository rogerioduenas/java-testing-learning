# 🏆 EXERCISE 10 — FINAL CHALLENGE: Payment and Delivery Orchestrator

> **Objective:** this is your final JUnit 5 + Mockito test.
> Unlike the previous exercises, **you will not be given the testing strategy or which tools to use**.
>
> > You must analyze the rules, identify the important behaviors, and decide on your own how to test them.

---

## 1. 🎯 Challenge

You have received a legacy class responsible for processing a complete order.

It:

1. validates the order;
2. checks the inventory;
3. processes the payment;
4. creates a transaction;
5. reserves the inventory;
6. dispatches the order for delivery;
7. records an audit.

There are **5 external dependencies** and several business rules.

Your goal is to create a **complete and reliable unit test suite**, protecting the rules below.

---

# 2. 📄 Business Rules

## Input

- `orderId` must be greater than zero.
- `customerEmail` must not be `null` or blank.
- `amount` must be greater than zero.
- `productId` must not be `null` or blank.
- `quantity` must be greater than zero.

### Inventory

- The service must check the inventory before attempting to process the payment.
- If there is insufficient inventory:
    - throws `InsufficientStockException`;
    - **does not process the payment**;
    - **does not create a transaction**;
    - **does not reserve inventory**;
    - **does not dispatch the order for delivery**;
    - **does not record a successful audit**.

### Payment

- If the payment is rejected:
    - throws `PaymentRejectedException`;
    - does not create a transaction;
    - does not reserve inventory;
    - does not dispatch the order for delivery.

### Success

When everything is correct:

1. checks the inventory;
2. processes the payment;
3. creates the transaction;
4. reserves the inventory;
5. dispatches the order for delivery;
6. records an audit;
7. returns the `PaymentResult`.

This order **is part of the business rule**.

### Transaction

The created transaction must contain:

- `orderId`
- `customerEmail`
- `amount`
- status `"PAID"`

The object is created **internally** by the service.

### Inventory Reservation

The reservation must use:

- `productId`
- `quantity`
- `orderId`

### Reservation Failure

If the inventory reservation throws `StockReservationException`:

- the exception must be propagated;
- **the order must not be dispatched for delivery**;
- **a successful audit must not be recorded**.

### Audit

On success, it must record:

- `orderId`
- `"PAYMENT_COMPLETED"`

---

# 3. 💻 Legacy Code

```java
public class PaymentResult {

    private final long orderId;
    private final String status;

    public PaymentResult(long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}
```

```java
public class PaymentTransaction {

    private final long orderId;
    private final String customerEmail;
    private final double amount;
    private final String status;

    public PaymentTransaction(
            long orderId,
            String customerEmail,
            double amount,
            String status) {

        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.amount = amount;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
```

```java
public interface StockService {

    int getAvailableQuantity(String productId);
}
```

```java
public interface PaymentGateway {

    PaymentResult processPayment(
            long orderId,
            String customerEmail,
            double amount);
}
```

```java
public interface TransactionRepository {

    void save(PaymentTransaction transaction);
}
```

```java
public interface StockReservationService {

    void reserve(
            String productId,
            int quantity,
            long orderId);
}
```

```java
public interface DeliveryService {

    void dispatch(long orderId);
}
```

```java
public interface AuditService {

    void log(long orderId, String event);
}
```

```java
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
```

```java
public class PaymentRejectedException extends RuntimeException {

    public PaymentRejectedException(String message) {
        super(message);
    }
}
```

```java
public class StockReservationException extends RuntimeException {

    public StockReservationException(String message) {
        super(message);
    }
}
```
# 4. 🚨 Legacy Class

```java
public class PaymentOrderService {

  private final StockService stockService;
  private final PaymentGateway paymentGateway;
  private final TransactionRepository transactionRepository;
  private final StockReservationService stockReservationService;
  private final DeliveryService deliveryService;
  private final AuditService auditService;

  public PaymentOrderService(
      StockService stockService,
      PaymentGateway paymentGateway,
      TransactionRepository transactionRepository,
      StockReservationService stockReservationService,
      DeliveryService deliveryService,
      AuditService auditService) {

    this.stockService = stockService;
    this.paymentGateway = paymentGateway;
    this.transactionRepository = transactionRepository;
    this.stockReservationService = stockReservationService;
    this.deliveryService = deliveryService;
    this.auditService = auditService;
  }

  public PaymentResult processOrder(
      long orderId,
      String customerEmail,
      String productId,
      int quantity,
      double amount) {

    int available = stockService.getAvailableQuantity(productId);

    if (available < quantity) {
      throw new RuntimeException("Wrong exception");
    }

    PaymentResult result =
        paymentGateway.processPayment(
            orderId,
            customerEmail,
            amount);

    PaymentTransaction transaction =
        new PaymentTransaction(
            orderId,
            customerEmail,
            amount,
            "PENDING");

    transactionRepository.save(transaction);

    stockReservationService.reserve(
        productId,
        quantity,
        orderId);

    deliveryService.dispatch(orderId);

    auditService.log(
        orderId,
        "PAYMENT_STARTED");

    return result;
  }
}
```

# 5. 🧠 Your Mission

Create the test suite for `PaymentOrderService`.

### ⚠️ I will not tell you which techniques to use.

You must decide on your own when it makes sense to use:

* JUnit assertions
* `assertThrows`
* `@ParameterizedTest`
* Mockito mocks
* BDDMockito
* `verify`
* `verifyNoInteractions`
* `ArgumentCaptor`
* `argThat`
* `InOrder`
* `doThrow`
* etc.

**Do not try to use every tool.**

Use only those that make sense to protect the behavior.

---

# 6. 🎯 Minimum Scenarios Your Test Suite Must Protect

You must discover the necessary tests from the rules, but your test suite must be able to detect at least:

### Input

* invalid `orderId`
* `null` email
* blank email
* invalid `productId`
* invalid quantity
* invalid amount

### Inventory

* sufficient inventory
* insufficient inventory

### Payment

* payment approved
* payment rejected

### Transaction

* transaction created correctly
* correct status
* correct data

### Reservation

* correct reservation
* reservation failure

### Flow

* correct operation order on success
* subsequent operations do not occur when a previous step fails

### Audit

* correct audit on success
* audit does not occur in failure flows