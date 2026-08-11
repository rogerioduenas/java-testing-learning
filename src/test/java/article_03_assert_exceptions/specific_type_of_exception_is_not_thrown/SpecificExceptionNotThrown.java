package article_03_assert_exceptions.specific_type_of_exception_is_not_thrown;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class SpecificExceptionNotThrown {

  private <T extends Exception> void assertSpecificExceptionIsNotThrown(Class<T> exceptionClass, Executable executable) {
    try {
      executable.execute();
    } catch (Exception e) {
      if (exceptionClass.isInstance(e)) {
        fail(e.getClass().getSimpleName() + " was thrown");
      } else {
        System.out.printf("Caught exception: " + e.getClass().getName() + ", but ignoring since it it not an instance of " + exceptionClass.getName());
      }
    }
  }

  @Test
  void givenASpecificExceptionType_whenBlockExecutes_thenEnsureThatExceptionIsNotThrown() {
    assertSpecificExceptionIsNotThrown(IllegalArgumentException.class, () -> {
      int i = 100 / 0;
    });
  }
}
