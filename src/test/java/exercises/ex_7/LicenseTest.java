package exercises.ex_7;

import com.rogerio.exercises.ex_7.License;
import com.rogerio.exercises.ex_7.LicenseValidatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LicenseTest {

  @Test
  @DisplayName("Should throw IllegalArgumentException when expiration date is null")
  void given_nullExpirationDate_when_instantiated_then_throwsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new License("123-abc", null));

    assertEquals("Expiration date must not be null", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException when product code is invalid")
  void given_invalidProductCode_when_instantiated_then_throwsIllegalArgumentException(String invalidProductCode) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new License(invalidProductCode, LocalDateTime.now()));

    assertEquals("Product code must not be blank", exception.getMessage());
  }
}
