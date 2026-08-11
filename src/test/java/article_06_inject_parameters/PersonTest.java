package article_06_inject_parameters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(PersonParameterResolver.class)
public class PersonTest {

  @Test
  public void testInvalidPerson(Person person) {
    assertNull(person.name());
  }

  @Test
  public void testValidPerson(Person person) {
    assertNotNull(person.name());
  }
}
