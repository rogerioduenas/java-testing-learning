package article_01_introduction;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryTest {

  @TestFactory
  Collection<DynamicTest> dynamicTestsForPositiveNumbers() {
    List<Integer> numbers = Arrays.asList(-1, 2, 5, -3, 10);

    return numbers.stream().map(number ->
        DynamicTest.dynamicTest("Validating if " + number + " is positive", () -> {
          assertTrue(number > 0, () -> "Number " + number + " should be greater than 0");
        })
    ).collect(Collectors.toList());
  }
}
