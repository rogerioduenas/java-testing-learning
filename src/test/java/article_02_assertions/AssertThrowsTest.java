package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertThrowsTest {

  @Test
  void whenAssertingException_thenThrown() {
    Throwable exception = assertThrows(
        IllegalArgumentException.class,
        () -> {
          throw new IllegalArgumentException("Exception message");
        }
    );
    assertEquals("Exception message", exception.getMessage());
  }

  @Test
  void assertThrowsException() {
    String str = null;
    assertThrows(IllegalArgumentException.class, () -> Integer.valueOf(str));
  }
}
