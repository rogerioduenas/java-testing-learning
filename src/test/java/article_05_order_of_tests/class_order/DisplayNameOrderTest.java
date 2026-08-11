package article_05_order_of_tests.class_order;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

@TestClassOrder(ClassOrderer.DisplayName.class)
public class DisplayNameOrderTest {

  @Nested
  @DisplayName("Class C")
  class Z extends TestC {}

  @Nested
  @DisplayName("Class B")
  class A extends TestA {}

  @Nested
  @DisplayName("Class A")
  class B extends TestB {}
}
