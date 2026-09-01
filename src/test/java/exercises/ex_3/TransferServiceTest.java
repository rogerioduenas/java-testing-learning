package exercises.ex_3;

import com.rogerio.exercises.ex_3.Account;
import com.rogerio.exercises.ex_3.AccountRepository;
import com.rogerio.exercises.ex_3.AuditLogger;
import com.rogerio.exercises.ex_3.TransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private AuditLogger auditLogger;

  @InjectMocks
  private TransferService transferService;

  @ParameterizedTest
  @ValueSource(longs = {0, -1L, -10L})
  @DisplayName("Should throw exception if account IDs are zero or negative")
  void given_zeroOrNegativeAccountId_when_executeTransfer_then_throwsIllegalArgumentException(long invalidId) {
    assertAll(
        () -> {
          IllegalArgumentException ex = assertThrows(
              IllegalArgumentException.class,
              () -> transferService.executeTransfer(invalidId, 2L, 100.0)
          );
          assertEquals("From account id must be positive", ex.getMessage());
        },
        () -> {
          IllegalArgumentException ex = assertThrows(
              IllegalArgumentException.class,
              () -> transferService.executeTransfer(1L, invalidId, 100.0)
          );
          assertEquals("To account id must be positive", ex.getMessage());
        }
    );

    then(accountRepository).shouldHaveNoInteractions();
    then(auditLogger).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should throw an exception if an attempt to transfer to the same accountId.")
  void given_sameSourceAndDestinationAccount_when_executeTransfer_then_throwsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> transferService.executeTransfer(1L, 1L, 100.0)
    );

    assertEquals("Source and destination accounts must be different",
        exception.getMessage());

    then(accountRepository).shouldHaveNoInteractions();
    then(auditLogger).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw exception if amount is zero or negative")
  void given_zeroOrNegativeAmount_when_executeTransfer_then_throwsIllegalArgumentException(double invalidAmount) {
    Account fromAccount = new Account(1L, 100.0);
    Account toAccount = new Account(2L, 100.0);

    given(accountRepository.findById(1L)).willReturn(fromAccount);
    given(accountRepository.findById(2L)).willReturn(toAccount);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> transferService.executeTransfer(1L, 2L, invalidAmount)
    );

    assertEquals("Amount must be positive", exception.getMessage());

    then(accountRepository).should(never()).update(any());
    then(auditLogger).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should update accounts and record transfer logs in strict order")
  void given_validAccountsAndSufficientBalance_when_executeTransfer_then_updatesAccountsAndLogsInStrictOrder() {
    Account fromAccount = new Account(1L, 500.0);
    Account toAccount = new Account(2L, 100.0);

    given(accountRepository.findById(1L)).willReturn(fromAccount);
    given(accountRepository.findById(2L)).willReturn(toAccount);

    transferService.executeTransfer(1L, 2L, 100.0);

    assertAll(
        () -> assertEquals(400.0, fromAccount.getBalance()),
        () -> assertEquals(200.0, toAccount.getBalance())
    );

    InOrder inOrder = inOrder(accountRepository, auditLogger);

    inOrder.verify(accountRepository).findById(1L);
    inOrder.verify(accountRepository).findById(2L);
    inOrder.verify(accountRepository).update(fromAccount);
    inOrder.verify(accountRepository).update(toAccount);
    inOrder.verify(auditLogger).logTransfer(1L, 2L, 100.0);

    inOrder.verifyNoMoreInteractions();
  }

  @Test
  @DisplayName("Should throw exception or change state correctly")
  void given_insufficientBalance_when_executeTransfer_then_throwsIllegalStateExceptionAndDoesNotUpdateDatabase() {
    Account fromAccount = new Account(1L, 100.0);
    Account toAccount = new Account(2L, 100.0);

    given(accountRepository.findById(1L)).willReturn(fromAccount);
    given(accountRepository.findById(2L)).willReturn(toAccount);

    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> transferService.executeTransfer(1L, 2L, 200.0)
    );

    assertEquals("Insufficient funds", exception.getMessage());

    then(accountRepository).should(never()).update(any());
    then(auditLogger).shouldHaveNoInteractions();
  }
}
