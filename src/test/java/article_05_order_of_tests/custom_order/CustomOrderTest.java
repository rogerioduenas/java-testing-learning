package article_05_order_of_tests.custom_order;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(CustomOrder.class)
public class CustomOrderTest {

  private static final StringBuilder output = new StringBuilder("");

  @Test
  void myBTest() {
    output.append("B");
  }

  @Test
  void myaTest() {
    output.append("a");
  }

  @Test
  void myATest() {
    output.append("A");
  }


  @AfterAll
  public static void assertOutput() {
    assertEquals("AaB", output.toString());
  }
}
