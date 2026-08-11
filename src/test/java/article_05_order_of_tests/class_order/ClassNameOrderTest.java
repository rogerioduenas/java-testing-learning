package article_05_order_of_tests.class_order;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestClassOrder;

@TestClassOrder(ClassOrderer.ClassName.class)
public class ClassNameOrderTest {

  @Nested
  class C extends TestC {}

  @Nested
  class B extends TestB {}

  @Nested
  class A extends TestA {}
}
