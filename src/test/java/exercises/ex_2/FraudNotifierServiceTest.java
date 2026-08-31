package exercises.ex_2;

import com.rogerio.exercises.ex_2.FraudNotifierService;
import com.rogerio.exercises.ex_2.FraudRepository;
import com.rogerio.exercises.ex_2.RiskAssessmentService;
import com.rogerio.exercises.ex_2.SmsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class FraudNotifierServiceTest {

  @Mock
  private RiskAssessmentService riskAssessmentService;

  @Mock
  private FraudRepository fraudRepository;

  @Mock
  private SmsService smsService;

  @InjectMocks
  private FraudNotifierService fraudNotifierService;

  @ParameterizedTest
  @ValueSource(ints = {0, -1, -10})
  @DisplayName("Should throw exception if AccountId is negative")
  void given_negativeAccountId_when_analyze_then_throwsIllegalArgumentException(int negativeAccountId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> fraudNotifierService.analyzeAndNotify(negativeAccountId, 1, 1)
    );

    assertEquals("AccountId must be positive", exception.getMessage());

    then(riskAssessmentService).shouldHaveNoInteractions();
    then(fraudRepository).shouldHaveNoInteractions();
    then(smsService).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(ints = {0, -1, -10})
  @DisplayName("Should throw exception if transactionId is negative")
  void given_negativeTransactionId_when_analyze_then_throwsIllegalArgumentException(int negativeTransactionId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> fraudNotifierService.analyzeAndNotify(1, negativeTransactionId, 1)
    );

    assertEquals("TransactionId must be positive", exception.getMessage());

    then(riskAssessmentService).shouldHaveNoInteractions();
    then(fraudRepository).shouldHaveNoInteractions();
    then(smsService).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw exception if amountId is negative")
  void given_negativeAmount_when_analyze_then_throwsIllegalArgumentException(double negativeAmount) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> fraudNotifierService.analyzeAndNotify(1, 1, negativeAmount)
    );

    assertEquals("Amount must be greater than 0", exception.getMessage());

    then(riskAssessmentService).shouldHaveNoInteractions();
    then(fraudRepository).shouldHaveNoInteractions();
    then(smsService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should return false and doesn't save or notify")
  void given_lowRiskTransaction_when_analyze_then_doesNotSaveOrNotifyAndReturnsFalse() {
    given(riskAssessmentService.getRiskScore(1, 1.0)).willReturn(79);

    boolean result = fraudNotifierService.analyzeAndNotify(1, 1, 1);

    assertFalse(result);
    then(fraudRepository).shouldHaveNoInteractions();
    then(smsService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should return true save fraud and send sms")
  void given_highRiskTransaction_when_analyze_then_savesFraudAndSendsSmsAndReturnsTrue() {
    given(riskAssessmentService.getRiskScore(1, 1.0)).willReturn(80);

    boolean result = fraudNotifierService.analyzeAndNotify(1, 1, 1);

    assertTrue(result);
    then(fraudRepository).should().saveAlert(1, 1, 80);
    then(smsService).should().sendAlert(1, "High risk detected");
  }

  @Test
  @DisplayName("Should throw exception if something went wrong")
  void given_riskAssessmentFails_when_analyze_then_throwsExceptionAndHasNoSecondaryInteractions() {
    given(riskAssessmentService.getRiskScore(1, 1.0)).willThrow(new RuntimeException("Something went wrong"));

    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> fraudNotifierService.analyzeAndNotify(1, 1, 1.0)
    );

    assertEquals("Something went wrong", exception.getMessage());
    then(fraudRepository).shouldHaveNoInteractions();
    then(smsService).shouldHaveNoInteractions();
  }
}
