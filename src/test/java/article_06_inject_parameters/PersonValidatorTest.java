package article_06_inject_parameters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Person Validator Tests")
public class PersonValidatorTest {

  @Nested
  @DisplayName("Valid Scenarios")
  @ExtendWith(PersonParameterResolver.class)
  class ValidData {

    @RepeatedTest(3)
    @DisplayName("Must accept valid data")
    void testValidPerson(Person person) {
      assertNotNull(person.id());
      assertNotNull(person.name());
    }
  }

  @Nested
  @DisplayName("Invalid Scenarios")
  @ExtendWith(PersonParameterResolver.class)
  class InvalidData {

    @RepeatedTest(3)
    @DisplayName("It must identify null data.")
    void testInvalidPerson(Person person) {
      assertNull(person.id());
      assertNull(person.name());
    }
  }
}
