package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertAllTest {

  @Test
  void givenMultipleAssertion_whenAssertingAll_thenOK() {
    Object obj = null;
    assertAll(
        "heading",
        () -> assertEquals(4, 2 * 2, "4 is 2 times 2"),
        () -> assertEquals("java", "JAVA".toLowerCase()),
        () -> assertNull(obj, "obj is null")
    );
  }
}
