package article_07_repeated_test;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

public class ConfiguringTestNameTest {

  @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
  void repeatedTestWithLongName() {
    System.out.println("Executing repeated test with long name");
  }

  @RepeatedTest(value = 3, name = "Custom name {currentRepetition}/{totalRepetitions}")
  void repeatedTestWithCustomDisplayName(TestInfo testInfo) {
    System.out.printf("Executing repeated test with custom name: (%s)%n", testInfo.getDisplayName());
  }
}
