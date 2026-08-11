package article_05_order_of_tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class AlphanumericMethodNameTest {
  private static StringBuilder output = new StringBuilder("");

  @Test
  void mycTest() {
    output.append("c");
  }

  @Test
  void myaTest() {
    output.append("a");
  }

  @Test
  void mybTest() {
    output.append("b");
  }

  @AfterAll
  public static void assertOutput() {
    assertEquals("abc", output.toString());
  }
}
