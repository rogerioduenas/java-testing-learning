package article_05_order_of_tests.class_order.custom_class_order;

import article_05_order_of_tests.class_order.TestA;
import article_05_order_of_tests.class_order.TestB;
import article_05_order_of_tests.class_order.TestC;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

@TestClassOrder(CustomClassOrder.class)
public class CustomClassOrderTest {
  @Nested
  class Longest extends TestA {}

  @Nested
  class Middle extends TestB {}

  @Nested
  class Short extends TestC {}
}
