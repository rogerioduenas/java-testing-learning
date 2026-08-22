# 🧪 Java Testing Journey (JUnit 5 & Mockito)

A repository focused on mastering unit testing, mocking techniques, and test-driven development practices in Java through a structured, article-driven, and practical approach.

---

## 📖 About the Repository

This repository represents the bridge between core language fundamentals and modern enterprise application development, building upon the knowledge acquired in the [Java Backend Learning Journey](https://github.com/rogerioduenas/java-learning).

Unit testing is an essential discipline for building robust, maintainable, and regression-free software. This repository systematically explores the JUnit 5 and Mockito ecosystems, moving from basic assertions to advanced mocking behaviors, lifecycle management, and architectural testing patterns.

### 🎯 Main Objectives

- Structured and progressive mastery of JUnit 5 and Mockito 5
- Hands-on practice with isolated, scenario-focused test classes
- Deep understanding of stubbing, argument matching, verification, and lifecycle hooks
- Foundation setup for integration testing in Spring Boot applications

---

## 📂 Repository Philosophy

Following the self-directed learning approach of this journey, this repository was built by exploring technical articles, framework documentation, and real-world testing scenarios.

Rather than relying purely on abstract theory, each module isolates a core testing concept, demonstrating both standard framework features and edge cases encountered in enterprise production code.

The goal is to develop the discipline to write clean, maintainable, and expressive unit tests that act as living documentation for the codebase.

---

## 🧩 Project Structure

The project follows a modular structure where each directory corresponds to a specific technical topic:

```text
JUnit 5 Core Fundamentals
          │
          ▼
Assertions, Exceptions & Life Cycle
          │
          ▼
Extensions & Dynamic Tests
          │
          ▼
Mockito Injection & Behavior (when/then)
          │
          ▼
Advanced Mocking (BDD, Captors, Static & Abstract Classes)
```

Each `article_xx_*` folder is self-contained, containing domain models, services, and dedicated unit test classes demonstrating specific testing mechanics.

---

## 🗺️ Learning Path

| # | Article / Topic | Core Concepts Covered | Status |
|---:|---|---|:---:|
| 01 | Introduction | Framework Setup, Basic Lifecycle, Assumptions | ✅ Completed |
| 02 | Assertions | `assertAll`, `assertArrayEquals`, `assertTimeout`, `linesMatch` | ✅ Completed |
| 03 | Assert Exceptions | `assertThrows`, Specific Exception Verification | ✅ Completed |
| 04 | Before / After | `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll` | ✅ Completed |
| 05 | Test Execution Order | `@TestMethodOrder`, `@TestClassOrder`, Custom Orderers | ✅ Completed |
| 06 | Inject Parameters | `ParameterResolver`, `@ExtendWith`, Custom Extensions | ✅ Completed |
| 07 | Repeated Tests | `@RepeatedTest`, `RepetitionInfo`, `FailureThreshold` | ✅ Completed |
| 08 | Dynamic Tests | `@TestFactory`, `DynamicTest`, Stream Generation | ✅ Completed |
| 09 | Resource Path | Reading Test Resources via `ClassLoader` & NIO `Path` | ✅ Completed |
| 10 | Mockito Extension | `MockitoExtension`, Parameter Injection in Tests | ✅ Completed |
| 11 | Mock Injection | `@Mock`, `@Spy`, `@Captor`, `@InjectMocks` | ✅ Completed |
| 12 | When / Then | Behavior Configuration (`when().thenReturn()`) | ✅ Completed |
| 13 | Argument Matchers | `eq()`, `argThat()`, Custom Matchers, Varargs | ✅ Completed |
| 14 | Exception Throwing | `thenThrow()`, `doThrow()`, Exception Stubbing | ✅ Completed |
| 15 | Verification | `verify()`, `verifyNoInteractions()`, In-Order Verification | ✅ Completed |
| 16 | Void Methods | `doNothing()`, `doAnswer()`, `doCallRealMethod()` | ✅ Completed |
| 17 | Lambdas & Optionals | Matching Optionals, Streams, Custom Answers via Lambdas | ✅ Completed |
| 18 | BDDMockito | BDD Syntax (`given()`, `willReturn()`, `then()`) | ✅ Completed |
| 19 | Strict Stubbing | `@MockitoSettings`, `lenient()`, `UnnecessaryStubbingException` | ✅ Completed |
| 20 | Argument Captor | `ArgumentCaptor`, Parameter Inspection & State Verification | ✅ Completed |
| 21 | Spy Behaviors | Partial Mocking, Real Method Execution, `doReturn()` vs `when()` | ✅ Completed |
| 22 | Static Methods | `mockStatic()`, `MockedStatic`, Thread Registration | ✅ Completed |
| 23 | Abstract Classes | Mocking Abstract Classes using `CALLS_REAL_METHODS` | ✅ Completed |

---

## 🛠️ Main Topics & Technologies Covered

### JUnit 5 (Jupiter)

- Test Lifecycle & Execution Ordering
- Advanced Assertions & Grouped Assertions (`assertAll`)
- Dynamic Tests & Test Factories (`@TestFactory`)
- Extension Model (`ParameterResolver`, `@ExtendWith`)
- Repeated Tests & Failure Thresholds
- Resource Path Resolution for Test Fixtures

### Mockito 5

- Mock Creation & Injection (`@Mock`, `@Spy`, `@InjectMocks`)
- Stubbing Techniques (`when/then`, `doReturn/when`)
- Void Method Handling (`doAnswer`, `doThrow`)
- Argument Matchers & Custom Lambda Matchers
- Argument Captors (`ArgumentCaptor`)
- BDD Style Testing (`BDDMockito`)
- Strict Stubbing & Unnecessary Stubbing Detection
- Static Method Mocking (`mockStatic`)
- Partial Mocking & Abstract Class Testing (`CALLS_REAL_METHODS`)

---

## 🚀 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/rogerioduenas/junit-mockito-learning.git
   ```

2. Open the project in your IDE (recommended: IntelliJ IDEA).

3. Ensure JDK 21+ is configured.

4. Run all tests via Maven:

   ```bash
   mvn test
   ```

Or execute individual test classes directly within your IDE.

---

## 🛠️ Technologies Used

- Java JDK 21 (Temurin)
- JUnit 5 (Jupiter Engine)
- Mockito 5
- Maven
- IntelliJ IDEA

---

## ⏭️ Next Steps

This repository completes the core unit testing phase of the learning journey.

The next stage continues in the [Spring Boot Backend Learning Journey](https://github.com/rogerioduenas/spring-boot-learning), where these unit testing and mocking foundations are applied to:

- Integration testing
- REST controller testing
- Database testing with Spring Data JPA
- Security layer testing
