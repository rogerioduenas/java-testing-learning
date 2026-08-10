package article_02_assertions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class AssertIterableEqualsTest {

  @Test
  void givenTwoLists_whenAssertingIterables_thenEquals() {
    Iterable<String> al = new ArrayList<>(asList("Java", "Junit", "Test"));
    Iterable<String> ll = new LinkedList<>(asList("Java", "Junit", "Test"));

    assertIterableEquals(al, ll);
  }
}
