### 🚀 EXERCISE 09 — Checkout Processor with Coupon and Fraud (`CheckoutProcessorService`)

#### 1. 🎯 Focus & Tools

- `@Mock`, `@InjectMocks`, `BDDMockito` (`given`, `then`).
- `ArgumentCaptor` to inspect the order/consolidated object saved to the database.
- Exception handling with `assertThrows` and verification of no interactions (`shouldHaveNoInteractions`).

#### 2. 📄 Exercise Description and Business Rules

The `CheckoutProcessorService` consolidates an order, applies coupon discount rules, checks the customer's risk, and persists the transaction.

- **Business Rules:**
    - `customerEmail` and `couponCode` must not be empty. `rawAmount` must be greater than zero (`> 0`).
    - If the fraud service (`FraudCheckService`) indicates that the customer is high-risk (`isHighRisk = true`), the checkout is interrupted with `IllegalStateException` and the transaction is **NOT** saved.
    - The discount coupon must be validated via `CouponService`. If the current date (`LocalDateTime.now()`) is later than the coupon's expiration date, the coupon is ignored (0% discount), but the checkout proceeds.
    - If the coupon is valid, apply the discount percentage to the final amount.
    - A `CheckoutOrder` must be instantiated, saved via `OrderRepository`, and an event must be triggered via `EventPublisher`.
    - The test **MUST capture the** **`CheckoutOrder`** **sent to the repository** and validate the net amount, creation date, and `"APPROVED"` status.

#### 3. 💻 Legacy Code (Copy into the IDE)

Java

```java
public class Coupon {
  private final String code;
  private final double discountPercentage;
  private final LocalDateTime expirationDate;

  public Coupon(String code, double discountPercentage, LocalDateTime expirationDate) {
    this.code = code;
    this.discountPercentage = discountPercentage;
    this.expirationDate = expirationDate;
  }

  public String getCode() { return code; }
  public double getDiscountPercentage() { return discountPercentage; }
  public LocalDateTime getExpirationDate() { return expirationDate; }
}
```

```java
public class CheckoutOrder {
  private final String customerEmail;
  private final double finalAmount;
  private final LocalDateTime createdAt;
  private final String status;

  public CheckoutOrder(String customerEmail, double finalAmount, LocalDateTime createdAt, String status) {
    this.customerEmail = customerEmail;
    this.finalAmount = finalAmount;
    this.createdAt = createdAt;
    this.status = status;
  }

  public String getCustomerEmail() { return customerEmail; }
  public double getFinalAmount() { return finalAmount; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public String getStatus() { return status; }
}
```

```java
public interface FraudCheckService { boolean isHighRisk(String email); }
public interface CouponService { Coupon findCoupon(String code); }
public interface OrderRepository { void save(CheckoutOrder order); }
public interface EventPublisher { void publishCheckoutEvent(CheckoutOrder order); }
```

```java
public class CheckoutProcessorService {

  private final FraudCheckService fraudCheckService;
  private final CouponService couponService;
  private final OrderRepository orderRepository;
  private final EventPublisher eventPublisher;

  public CheckoutProcessorService(FraudCheckService fraudCheckService, CouponService couponService,
                                  OrderRepository orderRepository, EventPublisher eventPublisher) {
    this.fraudCheckService = fraudCheckService;
    this.couponService = couponService;
    this.orderRepository = orderRepository;
    this.eventPublisher = eventPublisher;
  }

  public void processCheckout(String email, double rawAmount, String couponCode) {
    Coupon coupon = couponService.findCoupon(couponCode);
    double discount = coupon != null ? coupon.getDiscountPercentage() : 0.0;
    double finalAmount = rawAmount - (rawAmount * (discount / 100.0));

    CheckoutOrder order = new CheckoutOrder(email, finalAmount, LocalDateTime.now(), "PENDING");
    orderRepository.save(order);
    eventPublisher.publishCheckoutEvent(order);
  }
}
```