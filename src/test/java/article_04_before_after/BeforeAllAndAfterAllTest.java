package article_04_before_after;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BeforeAllAndAfterAllTest {

  @BeforeAll
  static void setup() {
    System.out.println("startup - creating DB connection");
  }

  @AfterAll
  static void tearDown() {
    System.out.println("closing DB connection");
  }

  @Test
  public void simpleTest() {
    System.out.println("simples test");
  }

  @Test
  public void anotherSimpleTest() {
    System.out.println("Another simple test");
  }
}
