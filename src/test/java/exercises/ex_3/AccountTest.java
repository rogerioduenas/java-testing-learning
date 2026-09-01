package exercises.ex_3;

import com.rogerio.exercises.ex_3.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
  @Test
  @DisplayName("Should debit amount successfully when balance is sufficient")
  void given_sufficientBalance_when_debit_then_decreasesBalance() {
    Account account = new Account(1L, 100.0);

    account.debit(40.0);

    assertEquals(60.0, account.getBalance());
  }

  @Test
  @DisplayName("Should credit amount successfully")
  void given_validAmount_when_credit_then_increasesBalance() {
    Account account = new Account(1L, 100.0);

    account.credit(50.0);

    assertEquals(150.0, account.getBalance());
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw exception if amount is negative")
  void given_invalidAmount_when_credit_then_throwIllegalArgumentException(double invalidAmount) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Account(1, 100.0).credit(invalidAmount)
    );
    assertEquals("Amount must be positive", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {-0.01, -10.0})
  @DisplayName("Should throw exception if balance is negative")
  void given_invalidBalance_when_createAccount_then_throwIllegalArgumentException(double invalidBalance) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Account(1, invalidBalance)
    );
    assertEquals("Balance can't be negative", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(longs = {0, -1L, -10L})
  @DisplayName("Should throw exception if invalid id used")
  void given_invalidId_when_createAccount_then_throwIllegalArgumentException(long invalidId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Account(invalidId, 0.0)
    );
    assertEquals("Account ID can't be negative", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw exception if debit amount exceeds the balance available")
  void given_validAccount_when_debitAmountExceedsBalance_then_IllegalStateException() {
    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> new Account(1, 100.0).debit(200.0)
    );
    assertEquals("Insufficient funds", exception.getMessage());
  }
}
