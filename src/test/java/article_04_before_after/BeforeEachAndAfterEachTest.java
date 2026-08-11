package article_04_before_after;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeforeEachAndAfterEachTest {

  private List<String> list;

  @BeforeEach
  public void init() {
    System.out.println("startup");
    list = new ArrayList<>(Arrays.asList("test1", "test2"));
  }

  @AfterEach
  public void teardown() {
    System.out.println("teardown");
    list.clear();
  }

  @Test
  public void whenCheckingListSize_thenSizeEqualsToInit() {
    System.out.println("executing test");
    assertEquals(2, list.size());

    list.add("another test");
  }

  @Test
  public void whenCheckingListSizeAgain_thenSizeEqualsToInit() {
    System.out.println("executing another test");
    assertEquals(2, list.size());

    list.add("yet another test");
  }
}
