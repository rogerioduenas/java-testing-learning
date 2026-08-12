package article_07_repeated_test;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FailureThresholdTest {

  @RepeatedTest(value = 10, failureThreshold = 2)
  void whenGeneratingRandomNumber_thenNumberShouldBeWithinRange() {
    int number = new Random().nextInt(10);
    assertTrue(number < 8);
  }
}
