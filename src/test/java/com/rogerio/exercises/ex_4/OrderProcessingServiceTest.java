package com.rogerio.exercises.ex_4;

import com.rogerio.exercises.ex_4.OrderProcessingService;
import com.rogerio.exercises.ex_4.OrderTransaction;
import com.rogerio.exercises.ex_4.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessingServiceTest {

  @Captor
  private ArgumentCaptor<OrderTransaction> captor;

  @Mock
  TransactionRepository repository;

  @InjectMocks
  private OrderProcessingService service;

  @ParameterizedTest
  @ValueSource(doubles = {100.0, 150.0})
  @DisplayName("Should apply 10% discount and status PROCESSED when amount is eligible (>= 100)")
  void given_amountEligibleForDiscount_when_process_then_capturesTransactionWithTenPercentDiscountAndProcessedStatus(double amount) {

    service.process("email@email.com", amount);

    verify(repository).save(captor.capture());

    assertEquals("email@email.com", captor.getValue().getCustomerEmail());
    assertEquals(amount, captor.getValue().getOriginalAmount());
    assertEquals(amount * 0.90, captor.getValue().getNetAmount());
    assertEquals("PROCESSED", captor.getValue().getStatus());
  }

  @ParameterizedTest
  @ValueSource(doubles = {99.99, 50.0})
  @DisplayName("Should process transaction without discount when amount is less than 100")
  void given_amountNotEligibleForDiscount_when_process_then_capturesTransactionWithoutDiscount(double amount) {
    service.process("email@email.com", amount);

    verify(repository).save(captor.capture());

    assertEquals(amount, captor.getValue().getNetAmount());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("Should throw an exception if email is null or blank")
  void given_invalidEmail_when_process_then_throwsIllegalArgumentExceptionWithoutCallingRepository(String invalidEmail) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> service.process(invalidEmail, 100.0)
    );
    assertEquals("Customer email cannot be blank", exception.getMessage());
    verifyNoInteractions(repository);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -1.0})
  @DisplayName("Should throw an exception if amount is negative")
  void given_invalidAmount_when_process_then_throwsIllegalArgumentExceptionWithoutCallingRepository(double invalidAmount) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> service.process("email@email.com", invalidAmount)
    );
    assertEquals("Amount must be positive", exception.getMessage());
    verifyNoInteractions(repository);
  }
}
