package article_01_introduction;

import org.junit.jupiter.api.*;

public class LifecycleTest {

  @BeforeAll
  public static void setup() {
    System.out.println("@BeforeAll - executes once before all test methods in this class");
  }

  @BeforeEach
  void init() {
    System.out.println("@BeforeEach - executes before each test method in this class");
  }

  @AfterEach
  void tearDown() {
    System.out.println("@AfterEach - executed after each test method.");
  }

  @AfterAll
  static void done() {
    System.out.println("@AfterAll - executed after all test methods.");
  }

  @Test
  void firstTest() {
    System.out.println("@Test - executing first test");
  }

  @Test
  void secondTest() {
    System.out.println("@Test - executing second test");
  }

  @DisplayName("Single test successful")
  @Test
  void testSingleSuccessTest() {
    System.out.println("Success");
  }

  @Test
  @Disabled("Not implemented yet")
  void testShowSomething() {
  }
}
