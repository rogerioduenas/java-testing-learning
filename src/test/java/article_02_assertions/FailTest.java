package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class FailTest {

  @Test
  public void whenFailingATest_thenFailed() {
    fail("FAIL - test not completed");
  }
}
