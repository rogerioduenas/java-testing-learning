package com.rogerio.exercises.ex_5;

import com.rogerio.exercises.ex_5.AccountBalanceService;
import com.rogerio.exercises.ex_5.FinancialReportGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancialReportGeneratorTest {

  @Mock
  private AccountBalanceService balanceService;

  @Spy
  @InjectMocks
  private FinancialReportGenerator reportGenerator;

  @ParameterizedTest
  @ValueSource(longs = {0, -1, -10})
  @DisplayName("Should throw an exception if account ID is invalid")
  void given_invalidAccountId_when_generateReport_then_throwsIllegalArgumentException(long invalidAccountId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> reportGenerator.generateReport(invalidAccountId)
    );

    assertEquals("Account ID must be positive", exception.getMessage());
    verifyNoInteractions(balanceService);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw an exception if balance is negative")
  void given_negativeOrZeroBalance_when_generateReport_then_throwsIllegalStateExceptionAndNeverCallsTaxApi(double invalidBalance) {
    when(balanceService.getBalance(1L)).thenReturn(invalidBalance);

    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> reportGenerator.generateReport(1L)
    );

    assertEquals("Balance must be positive", exception.getMessage());
    verify(balanceService).getBalance(1L);
    verify(reportGenerator, never()).fetchTaxRateFromExternalApi(anyLong());
  }

  @Test
  @DisplayName("Should calculate the tax rate correctly")
  void given_validAccount_when_generateReport_then_usesSpyToStubExternalTaxRateAndCalculatesCorrectly() {
    long accountId = 10L;
    when(balanceService.getBalance(accountId)).thenReturn(100.0);
    doReturn(0.15).when(reportGenerator).fetchTaxRateFromExternalApi(accountId);

    double result = reportGenerator.generateReport(accountId);

    assertEquals(15.0, result, 0.0001);
  }
}
