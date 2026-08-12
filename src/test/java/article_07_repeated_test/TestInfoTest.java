package article_07_repeated_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class TestInfoTest {

  @Test
  void repeatedTest(TestInfo testInfo) {
    System.out.printf("Test info: (%s)%n", testInfo);
  }
}
