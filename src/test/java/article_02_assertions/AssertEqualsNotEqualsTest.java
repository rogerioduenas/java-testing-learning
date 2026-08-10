package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssertEqualsNotEqualsTest {

  @Test
  void whenAssertingEquality_thenEqual() {
    float square = 2 * 2;
    float rectangle = 2 * 2;

    assertEquals(square, rectangle);
  }

  @Test
  void whenAssertingEqualityWithDelta_thenEqual() {
    float square = 2 * 2; // 4
    float rectangle = 3 * 2; // 6
    float delta = 2; // acceptable margin of error

    assertEquals(square, rectangle, delta);
  }

  @Test
  void whenAssertingEquality_thenNotEqual() {
    Integer value = 5;

    assertNotEquals(0, value, "The result cannot be 0");
  }
}
