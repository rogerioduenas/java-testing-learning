package article_01_introduction;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class AssertionsAndAssumptionsTest {

  @Test
  void lambdaExpressions() {
    List<Integer> numbers = Arrays.asList(1, 2, 3);
    assertTrue(numbers.stream()
        .mapToInt(Integer::intValue)
        .sum() > 5, () -> "Sum should be greater than 5");
  }

  @Test
  void groupAssertions() {
    int[] numbers = {1, 2, 3};
    assertAll("numbers",
        () -> assertEquals(numbers[0], 1),
        () -> assertEquals(numbers[1], 2),
        () -> assertEquals(numbers[2], 3)
    );
  }

  @Test
  void trueAssumption() {
    assumeTrue(5 > 1);
    assertEquals(5 + 2, 7);
  }

  @Test
  void falseAssumption() {
    assumeFalse(5 < 1);
    assertEquals(5 + 2, 7);
  }

  @Test
  void assumptionThat() {
    String someString = "Just a string";
    assumingThat(
        someString.equals("Just a tring"),
        () -> assertEquals(2 + 2, 4)
    );
  }
}
