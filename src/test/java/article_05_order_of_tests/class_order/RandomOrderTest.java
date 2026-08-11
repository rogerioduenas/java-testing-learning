package article_05_order_of_tests.class_order;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

@TestClassOrder(ClassOrderer.Random.class)
public class RandomOrderTest {
  @Nested
  class C extends TestC {}

  @Nested
  class B extends TestB {}

  @Nested
  class A extends TestA {}
}
