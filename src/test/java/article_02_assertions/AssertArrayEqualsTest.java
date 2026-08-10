package article_02_assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AssertArrayEqualsTest {

  @Test
  public void whenAssertingArraysEquality_thenEqual() {
    char[] expected = { 'J', 'u', 'p', 'i', 't', 'e', 'r' };
    char[] actual = "Jupiter".toCharArray();

    assertArrayEquals(expected, actual, "Arrays should be equal");
  }
}
