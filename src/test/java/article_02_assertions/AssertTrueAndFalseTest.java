package article_02_assertions;

import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertTrueAndFalseTest {

  @Test
  void whenAssertingConditions_thenVerified() {
    assertTrue(5 > 4, "5 is greater the 4");
  }

  @Test
  public void givenBooleanSupplier_whenAssertingCondition_thenVerified() {
    BooleanSupplier condition = () -> 5 > 6;

    assertFalse(condition, "5 is not greater then 6");
  }
}
