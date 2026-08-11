package article_05_order_of_tests;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AlphanumericDisplayNameTest {

  @Test
  @DisplayName("2")
  void testB() {
    System.out.println("2");
  }

  @Test
  @DisplayName("3")
  void testC() {
    System.out.println("3");
  }

  @Test
  @DisplayName("1")
  void testA() {
    System.out.println("1");
  }

}
