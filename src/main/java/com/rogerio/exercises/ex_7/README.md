### 🚀 EXERCISE 07 — License Expiration Validator with Static Time (`LicenseValidatorService`)

#### 1. 🎯 Focus & Techniques to Practice

- Mockito's `mockStatic(LocalDateTime.class)` to "freeze" or simulate the system date/time.
- Correct use of `try-with-resources` to ensure the static mock is closed after test execution (preventing contamination of other tests running on the same thread).
- Date boundary protections (`isBefore`, `isAfter`, nulls).

#### 2. 📄 Exercise Statement and Business Rules

The `LicenseValidatorService` checks whether a software license key is still valid by comparing the expiration date stored in the license with the current system date/time (`LocalDateTime.now()`).

- **Business Rules:**
    - The license (`License`) must not be null, and its expiration date (`expirationDate`) must not be null.
    - The license's product code (`productCode`) must not be null or blank.
    - If the current system date/time (`LocalDateTime.now()`) is **before or equal to** the license expiration date, the method must return `true` (valid license).
    - If the current system date/time is **strictly after** the expiration date, the method must return `false` (expired license).
    - The test **MUST freeze** **`LocalDateTime.now()`** **using** **`mockStatic`** to ensure deterministic expiration tests without depending on the machine's real-time clock.

#### 3. 💻 Legacy Code (Copy into the IDE)

Java

```java
public class License {
  private final String productCode;
  private final LocalDateTime expirationDate;

  public License(String productCode, LocalDateTime expirationDate) {
    this.productCode = productCode;
    this.expirationDate = expirationDate;
  }

  public String getProductCode() { return productCode; }
  public LocalDateTime getExpirationDate() { return expirationDate; }
}
```

```java
public class LicenseValidatorService {

  public boolean isLicenseValid(License license) {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(license.getExpirationDate());
  }
}
```
