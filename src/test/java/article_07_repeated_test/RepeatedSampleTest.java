package article_07_repeated_test;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

public class RepeatedSampleTest {

  @RepeatedTest(3)
  void repeatedTest(TestInfo testInfo) {
    System.out.printf("Executing repeated test (%s)%n", testInfo.getDisplayName());
  }
}
