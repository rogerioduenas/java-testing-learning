package article_05_order_of_tests.class_order;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestClassOrder;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class OrderAnnotationTest {

  @Nested
  @Order(3)
  class A extends TestA {
  }

  @Nested
  @Order(1)
  class B extends TestB {
  }

  @Nested
  @Order(2)
  class C extends TestC {
  }
}