package article_02_assertions;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class AssertTimeoutAndPreemptivelyTest {

  @Test
  void whenAssertingTimeout_thenNotExceeded() {
    assertTimeout(
        ofSeconds(2),
        () -> {
          Thread.sleep(5000);
        }
    );
  }

  @Test
  void whenAssertingTimeoutPreemptively_thenAbortedOnTimeout() {
    assertTimeoutPreemptively(
        ofSeconds(2),
        () -> {
          Thread.sleep(5000);
        }
    );
  }
}
