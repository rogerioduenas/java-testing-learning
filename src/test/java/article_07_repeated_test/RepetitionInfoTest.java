package article_07_repeated_test;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class RepetitionInfoTest {

  @RepeatedTest(3)
  void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
    System.out.println("Repetition #" + repetitionInfo);
  }
}
