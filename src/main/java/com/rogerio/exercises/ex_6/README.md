### 🚀 EXERCISE 06 — Notification Batch Processor with Mutation (`NotificationBatchProcessor`)

#### 1. 🎯 Focus & Techniques to Practice

- `doAnswer()` to manipulate received parameters, simulate state mutation of objects passed by reference, or generate dynamic return values.
- `argThat()` (Custom Argument Matchers with lambda expressions).

#### 2. 📄 Exercise Description & Business Rules

The `NotificationBatchProcessor` processes a `NotificationTask` object by updating its processing state through a callback from the `TaskExecutor`.

- **Business Rules:**
  - The provided `NotificationTask` object must not be null, and its message must not be empty.
  - The task must have an initial `retryCount` lower than 3. If it is `>= 3`, it must throw `IllegalArgumentException` (`"Max retries reached"`).
  - When calling `executor.execute(task)`, the mocked interface changes the task's internal state by calling `task.markAsCompleted()`.
  - In the test, you will use `doAnswer(...)` to simulate this mutation behavior of the executor and verify that `processor.processTask(task)` returns `true` only when the task has been properly marked as completed.

#### 3. 💻 Legacy Code (Copy into the IDE)
**Java**

```java
public class NotificationTask {
  private final String message;
  private int retryCount;
  private boolean completed;

  public NotificationTask(String message, int retryCount) {
    this.message = message;
    this.retryCount = retryCount;
    this.completed = false;
  }

  public String getMessage() { return message; }
  public int getRetryCount() { return retryCount; }
  public boolean isCompleted() { return completed; }
  public void markAsCompleted() { this.completed = true; }
}
```

```java
public interface TaskExecutor {
  void execute(NotificationTask task);
}
```


```java
public class NotificationBatchProcessor {

  private final TaskExecutor executor;

  public NotificationBatchProcessor(TaskExecutor executor) {
    this.executor = executor;
  }

  public boolean processTask(NotificationTask task) {
    executor.execute(task);
    return true; 
  }
}
```