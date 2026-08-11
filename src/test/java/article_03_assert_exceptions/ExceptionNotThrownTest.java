package article_03_assert_exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExceptionNotThrownTest {

  @Test
  void givenABlock_whenExecutes_thenEnsureNoExceptionThrown() {
    assertDoesNotThrow(() -> {
      Integer.parseInt("100");
    });
  }
}
