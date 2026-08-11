package article_05_order_of_tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.Random.class)
public class RandomOrderTest {

  private static StringBuilder output = new StringBuilder("");

  @Test
  void myATest() {
    output.append("A");
  }

  @Test
  void myBTest() {
    output.append("B");
  }

  @Test
  void myCTest() {
    output.append("C");
  }

  @AfterAll
  public static void showOutput() {
    System.out.println(output.toString());
  }
}
