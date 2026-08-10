package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AssertNullAndNotNullTest {

  @Test
  void whenAssertingNotNull_thenTrue() {
    Integer number = 1;
    assertNotNull(number, () -> "Number should not be null");
  }

  @Test
  void whenAssertingNull_thenTrue() {
    Integer number = null;
    assertNull(number, () -> "Number should be null");
  }
}
