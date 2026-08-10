package article_02_assertions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class AssertLinesMatchTest {

  @Test
  void whenAssertingEqualityListOfStrings_thenEqual() {
    List<String> expected = asList("Java", "\\d+", "JUnit");
    List<String> actual = asList("Java", "11", "JUnit");

    assertLinesMatch(expected, actual);
  }
}
