package exercises.ex_7;

import com.rogerio.exercises.ex_7.License;
import com.rogerio.exercises.ex_7.LicenseValidatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LicenseValidatorServiceTest {

  @InjectMocks
  private LicenseValidatorService licenseValidatorService;

  @Test
  @DisplayName("Should return true when current time is before expiration")
  void given_unexpiredLicense_when_isValid_then_returnsTrueWithFrozenTime() {
    LocalDateTime currentTime = LocalDateTime.of(2026, 9, 8, 12, 0);
    LocalDateTime expirationDate = currentTime.plusDays(1);
    License license = new License("123-abc", expirationDate);

    try (MockedStatic<LocalDateTime> mockedDateTime = Mockito.mockStatic(LocalDateTime.class)) {
      mockedDateTime.when(LocalDateTime::now).thenReturn(currentTime);

      boolean result = licenseValidatorService.isLicenseValid(license);

      assertTrue(result);
    }
  }

  @Test
  @DisplayName("Should return false when current time is after expiration")
  void given_expiredLicense_when_isValid_then_returnsFalseWithFrozenTime() {
    LocalDateTime currentTime = LocalDateTime.of(2026, 9, 8, 12, 0);
    LocalDateTime expirationDate = currentTime.minusDays(1);
    License license = new License("123-abc", expirationDate);

    try (MockedStatic<LocalDateTime> mockedDateTime = Mockito.mockStatic(LocalDateTime.class)) {
      mockedDateTime.when(LocalDateTime::now).thenReturn(currentTime);

      boolean result = licenseValidatorService.isLicenseValid(license);

      assertFalse(result);
    }
  }

  @Test
  @DisplayName("Should return true when current time and expiration are the same instant")
  void given_sameExpirationAndCurrentTime_when_isValid_then_returnsTrueWithFrozenTime() {
    LocalDateTime currentTime = LocalDateTime.of(2026, 9, 8, 12, 0);
    License license = new License("123-abc", currentTime);

    try (MockedStatic<LocalDateTime> mockedDateTime = Mockito.mockStatic(LocalDateTime.class)) {
      mockedDateTime.when(LocalDateTime::now).thenReturn(currentTime);

      boolean result = licenseValidatorService.isLicenseValid(license);

      assertTrue(result);
    }
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException when license is null")
  void given_nullLicense_when_isValid_then_throwsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> licenseValidatorService.isLicenseValid(null));

    assertEquals("Licence must not be null", exception.getMessage());
  }
}
