package article_02_assertions;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AssertSameAndNotSameTest {

  @Test
  void whenAssertingSameObject_thenSuccessfull() {
    String language = "Java";
    Optional<String> optional = Optional.of(language);

    assertSame(language, optional.get());
  }

  @Test
  void whenAssertingNotSameObject_thenSuccessfull() {
    String language = "Java";
    Optional<String> optional = Optional.of("Java");

    assertNotSame(language, optional);
  }
}
