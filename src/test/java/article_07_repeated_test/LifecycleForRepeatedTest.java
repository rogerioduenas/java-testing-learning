package article_07_repeated_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

public class LifecycleForRepeatedTest {

  @BeforeEach
  void beforeEachTest() {
    System.out.println("Before Each Repetition");
  }

  @AfterEach
  void afterEachTest() {
    System.out.println("After Each Repetition");
    System.out.println("=====================");
  }

  @RepeatedTest(value = 3)
  void whenGeneratingRandomNumber_thenNumberShouldBeWithinRange() {
    System.out.println("--- Executing Test ---");
  }
}
