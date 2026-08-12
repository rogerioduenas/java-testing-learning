package article_08_dynamic_tests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleDynamicTest {

  @TestFactory
  Collection<DynamicTest> testNumbers() {
    List<Integer> numbers = List.of(2, 1, 6, 8);

    return numbers.stream()
        .map(number -> DynamicTest.dynamicTest(
            "Testing if the number " + number + " is even",
            () -> assertEquals(0, number % 2)
        ))
        .toList();
  }
}
