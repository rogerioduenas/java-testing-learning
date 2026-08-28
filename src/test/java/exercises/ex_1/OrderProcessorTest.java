package exercises.ex_1;

import com.rogerio.exercises.ex_1.EmailService;
import com.rogerio.exercises.ex_1.OrderProcessor;
import com.rogerio.exercises.ex_1.PaymentGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest {

  @Mock
  private PaymentGateway paymentGateway;

  @Mock
  private EmailService emailService;

  @InjectMocks
  private OrderProcessor orderProcessor;

  @Test
  @DisplayName("Should return true and send email if payment was processed")
  void given_validOrder_when_paymentApproved_then_sendsEmailAndReturnsTrue() {
    given(paymentGateway.processPayment("email@email.com", 100.0)).willReturn(true);

    boolean result = orderProcessor.processOrder("email@email.com", 100.0);

    assertTrue(result);
    then(emailService).should().sendConfirmation("email@email.com", 100.0);
  }

  @Test
  @DisplayName("Should return false and doesn't send email if payment wasn't processed")
  void given_validOrder_when_paymentDeclined_then_doesNotSendEmailAndReturnsFalse() {
    given(paymentGateway.processPayment("email@email.com", 100.0)).willReturn(false);

    boolean result = orderProcessor.processOrder("email@email.com", 100.0);

    assertFalse(result);
    then(emailService).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw exception if invalid amount")
  void given_invalidAmount_when_processOrder_then_throwsIllegalArgumentException(double invalidAmount) {

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> orderProcessor.processOrder("email@email.com", invalidAmount)
    );

    assertEquals("Amount must be positive", exception.getMessage());
    then(paymentGateway).shouldHaveNoInteractions();
    then(emailService).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(strings = {" ", "  "})
  @NullAndEmptySource
  @DisplayName("Should throw exception if invalid email")
  void given_invalidEmail_when_processOrder_then_throwsIllegalArgumentException(String invalidEmail) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> orderProcessor.processOrder(invalidEmail, 100.0)
    );

    assertEquals("Customer email cannot be blank or null", exception.getMessage());
    then(paymentGateway).shouldHaveNoInteractions();
    then(emailService).shouldHaveNoInteractions();
  }
}