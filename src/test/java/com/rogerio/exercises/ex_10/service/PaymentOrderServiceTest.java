package com.rogerio.exercises.ex_10.service;

import com.rogerio.exercises.ex_10.exception.InsufficientStockException;
import com.rogerio.exercises.ex_10.exception.PaymentRejectedException;
import com.rogerio.exercises.ex_10.exception.StockReservationException;
import com.rogerio.exercises.ex_10.model.PaymentResult;
import com.rogerio.exercises.ex_10.model.PaymentTransaction;
import com.rogerio.exercises.ex_10.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentOrderServiceTest {

  @Mock
  private StockService stockService;

  @Mock
  private PaymentGateway paymentGateway;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private StockReservationService stockReservationService;

  @Mock
  private DeliveryService deliveryService;

  @Mock
  private AuditService auditService;
  @InjectMocks
  private PaymentOrderService paymentOrderService;

  @ParameterizedTest
  @ValueSource(longs = {0L, -1L, -10L})
  @DisplayName("Should throw IllegalArgumentException when order ID is non-positive")
  void givenInvalidOrderId_whenProcessOrder_thenThrowIllegalArgumentException(long invalidOrderId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> paymentOrderService.processOrder(invalidOrderId, "email@email.com", "VALID_PRODUCT_ID", 1, 100.0)
    );

    assertEquals("Order ID must be greater than zero", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException when customer email is null or blank")
  void givenInvalidCustomerEmail_whenProcessOrder_thenThrowIllegalArgumentException(String invalidCustomerEmail) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> paymentOrderService.processOrder(1L, invalidCustomerEmail, "VALID_PRODUCT_ID", 1, 100.0)
    );

    assertEquals("Customer email must be not blank or null", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException when product ID is null or blank")
  void givenInvalidProductId_whenProcessOrder_thenThrowIllegalArgumentException(String invalidProductId) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> paymentOrderService.processOrder(1L, "email@email.com", invalidProductId, 1, 100.0)
    );

    assertEquals("Product ID must be not blank or null", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, -1, -10})
  @DisplayName("Should throw IllegalArgumentException when quantity is non-positive")
  void givenInvalidQuantity_whenProcessOrder_thenThrowIllegalArgumentException(int invalidQuantity) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> paymentOrderService.processOrder(1L, "email@email.com", "VALID_PRODUCT_ID", invalidQuantity, 100.0)
    );

    assertEquals("Quantity must be greater than zero", exception.getMessage());
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -0.01, -10.0})
  @DisplayName("Should throw IllegalArgumentException when amount is non-positive")
  void givenInvalidAmount_whenProcessOrder_thenThrowIllegalArgumentException(double invalidAmount) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> paymentOrderService.processOrder(1L, "email@email.com", "VALID_PRODUCT_ID", 1, invalidAmount)
    );

    assertEquals("Amount must be greater than zero", exception.getMessage());
  }

  @Test
  @DisplayName("Should throw InsufficientStockException when stock is less than quantity")
  void givenInsufficientStock_whenProcessOrder_thenThrowInsufficientStockException() {
    String productId = "VALID_PRODUCT_ID";
    String expectedErrorMessage = String.format("Insufficient stock for product: %s", productId);

    given(stockService.getAvailableQuantity(productId)).willReturn(5);

    InsufficientStockException exception = assertThrows(
        InsufficientStockException.class,
        () -> paymentOrderService.processOrder(1L, "email@email.com", productId, 10, 100.0)
    );

    assertEquals(expectedErrorMessage, exception.getMessage());
    then(paymentGateway).shouldHaveNoInteractions();
    then(transactionRepository).shouldHaveNoInteractions();
    then(stockReservationService).shouldHaveNoInteractions();
    then(deliveryService).shouldHaveNoInteractions();
    then(auditService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should throw PaymentRejectedException when payment is declined by the gateway")
  void givenPaymentRejected_whenProcessOrder_thenThrowPaymentRejectedException() {
    long orderId = 1L;
    String customerEmail = "email@email.com";
    String productId = "VALID_PRODUCT_ID";
    double amount = 100.0;

    given(stockService.getAvailableQuantity(productId)).willReturn(10);
    given(paymentGateway.processPayment(orderId, customerEmail, amount))
        .willThrow(new PaymentRejectedException("Payment declined: insufficient funds"));

    PaymentRejectedException exception = assertThrows(
        PaymentRejectedException.class,
        () -> paymentOrderService.processOrder(orderId, customerEmail, productId, 1, amount)
    );

    assertEquals("Payment declined: insufficient funds", exception.getMessage());

    then(transactionRepository).shouldHaveNoInteractions();
    then(stockReservationService).shouldHaveNoInteractions();
    then(deliveryService).shouldHaveNoInteractions();
    then(auditService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should propagate StockReservationException when stock reservation fails")
  void givenStockReservationFails_whenProcessOrder_thenPropagateStockReservationException() {
    long orderId = 1L;
    String email = "email@email.com";
    String productId = "VALID_PRODUCT_ID";
    int quantity = 2;
    double amount = 100.0;

    given(stockService.getAvailableQuantity(productId)).willReturn(10);
    willThrow(new StockReservationException("Reservation system offline"))
        .given(stockReservationService).reserve(productId, quantity, orderId);

    StockReservationException exception = assertThrows(
        StockReservationException.class,
        () -> paymentOrderService.processOrder(orderId, email, productId, quantity, amount)
    );

    assertEquals("Reservation system offline", exception.getMessage());

    then(transactionRepository).should().save(any(PaymentTransaction.class));
    then(deliveryService).shouldHaveNoInteractions();
    then(auditService).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should return PAID status when processing a valid order")
  void givenValidOrder_whenProcessOrder_thenReturnPaidResult() {
    long orderId = 1L;
    String email = "email@email.com";
    String productId = "VALID_PRODUCT_ID";
    int quantity = 2;
    double amount = 100.0;

    given(stockService.getAvailableQuantity(productId)).willReturn(10);
    given(paymentGateway.processPayment(orderId, email, amount)).willReturn(new PaymentResult(orderId, "PAID"));

    PaymentResult result = paymentOrderService.processOrder(orderId, email, productId, quantity, amount);

    assertNotNull(result);
    assertEquals("PAID", result.getStatus());
  }

  @Test
  @DisplayName("Should save correct transaction details when processing a valid order")
  void givenValidOrder_whenProcessOrder_thenSavePaymentTransaction() {
    long orderId = 1L;
    String email = "email@email.com";
    String productId = "VALID_PRODUCT_ID";
    int quantity = 2;
    double amount = 100.0;

    given(stockService.getAvailableQuantity(productId)).willReturn(10);
    given(paymentGateway.processPayment(orderId, email, amount)).willReturn(new PaymentResult(orderId, "PAID"));

    paymentOrderService.processOrder(orderId, email, productId, quantity, amount);

    ArgumentCaptor<PaymentTransaction> txCaptor = ArgumentCaptor.forClass(PaymentTransaction.class);
    then(transactionRepository).should().save(txCaptor.capture());
    PaymentTransaction savedTx = txCaptor.getValue();
    assertEquals(orderId, savedTx.getOrderId());
    assertEquals(email, savedTx.getCustomerEmail());
    assertEquals(amount, savedTx.getAmount());
    assertEquals("PAID", savedTx.getStatus());
  }

  @Test
  @DisplayName("Should execute all steps in correct business order when processing a valid order")
  void givenValidOrder_whenProcessOrder_thenExecuteStepsInCorrectOrder() {
    long orderId = 1L;
    String email = "email@email.com";
    String productId = "VALID_PRODUCT_ID";
    int quantity = 2;
    double amount = 100.0;

    given(stockService.getAvailableQuantity(productId)).willReturn(10);
    given(paymentGateway.processPayment(orderId, email, amount)).willReturn(new PaymentResult(orderId, "PAID"));

    paymentOrderService.processOrder(orderId, email, productId, quantity, amount);

    InOrder inOrder = inOrder(stockService, paymentGateway, transactionRepository, stockReservationService, deliveryService, auditService);
    inOrder.verify(stockService).getAvailableQuantity(productId);
    inOrder.verify(paymentGateway).processPayment(orderId, email, amount);
    inOrder.verify(transactionRepository).save(any(PaymentTransaction.class));
    inOrder.verify(stockReservationService).reserve(productId, quantity, orderId);
    inOrder.verify(deliveryService).dispatch(orderId);
    inOrder.verify(auditService).log(orderId, "PAYMENT_COMPLETED");
  }
}
