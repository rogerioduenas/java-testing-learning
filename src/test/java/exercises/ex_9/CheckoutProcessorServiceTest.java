package exercises.ex_9;

import com.rogerio.exercises.ex_9.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class CheckoutProcessorServiceTest {

  @Mock
  private FraudCheckService fraudCheckService;

  @Mock
  private CouponService couponService;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private EventPublisher eventPublisher;

  @InjectMocks
  private CheckoutProcessorService checkoutProcessorService;

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException and avoid service interactions when email is null, empty, or blank")
  void givenInvalidEmail_whenProcessCheckout_thenThrowsIllegalArgumentExceptionWithoutInteractingWithServices(String invalidEmail) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> checkoutProcessorService.processCheckout(invalidEmail, 100.0, "VALID_COUPON")
    );

    assertEquals("Email address is invalid", exception.getMessage());

    then(fraudCheckService).shouldHaveNoInteractions();
    then(couponService).shouldHaveNoInteractions();
    then(orderRepository).shouldHaveNoInteractions();
    then(eventPublisher).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, -1.0, -100.0})
  @DisplayName("Should throw IllegalArgumentException and avoid service interactions when rawAmount is less than or equal to zero")
  void givenInvalidAmount_whenProcessCheckout_thenThrowsIllegalArgumentExceptionWithoutInteractingWithServices(double invalidAmount) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> checkoutProcessorService.processCheckout("email@email.com", invalidAmount, "VALID_COUPON")
    );

    assertEquals("RawAmount must be greater than zero", exception.getMessage());

    then(fraudCheckService).shouldHaveNoInteractions();
    then(couponService).shouldHaveNoInteractions();
    then(orderRepository).shouldHaveNoInteractions();
    then(eventPublisher).shouldHaveNoInteractions();
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n"})
  @NullAndEmptySource
  @DisplayName("Should throw IllegalArgumentException and avoid service interactions when coupon code is null, empty, or blank")
  void givenInvalidCouponCode_whenProcessCheckout_thenThrowsIllegalArgumentExceptionWithoutInteractingWithServices(String invalidCoupon) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> checkoutProcessorService.processCheckout("email@email.com", 100.0, invalidCoupon)
    );

    assertEquals("Coupon code can't be empty or null", exception.getMessage());

    then(fraudCheckService).shouldHaveNoInteractions();
    then(couponService).shouldHaveNoInteractions();
    then(orderRepository).shouldHaveNoInteractions();
    then(eventPublisher).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should throw IllegalStateException and abort transaction when customer is high risk")
  void givenHighRiskCustomer_whenProcessCheckout_thenThrowsExceptionAndAbortsTransaction() {
    given(fraudCheckService.isHighRisk(anyString())).willReturn(true);

    IllegalStateException exception = assertThrows(
        IllegalStateException.class,
        () -> checkoutProcessorService.processCheckout("email@email.com", 100.0, "VALID_COUPON")
    );

    assertEquals("Customer is high risk, transaction wasn't completed", exception.getMessage());
    then(couponService).shouldHaveNoInteractions();
    then(orderRepository).shouldHaveNoInteractions();
    then(eventPublisher).shouldHaveNoInteractions();
  }

  @Test
  @DisplayName("Should ignore discount and process full amount when coupon is expired")
  void givenExpiredCoupon_whenProcessCheckout_thenIgnoresDiscountAndProcessesWithFullAmount() {
    LocalDateTime expiredDate = LocalDateTime.now().minusDays(1);
    given(couponService.findCoupon("EXPIRED_COUPON"))
        .willReturn(new Coupon("EXPIRED_COUPON", 10.0, expiredDate));

    ArgumentCaptor<CheckoutOrder> orderCaptor = ArgumentCaptor.forClass(CheckoutOrder.class);

    checkoutProcessorService.processCheckout("email@email.com", 100.0, "EXPIRED_COUPON");

    then(orderRepository).should().save(orderCaptor.capture());
    CheckoutOrder savedOrder = orderCaptor.getValue();

    assertEquals(100.0, savedOrder.getFinalAmount());
    assertEquals("APPROVED", savedOrder.getStatus());
    assertNotNull(savedOrder.getCreatedAt());

    then(eventPublisher).should().publishCheckoutEvent(savedOrder);
  }

  @Test
  @DisplayName("Should apply discount and process full amount when coupon is valid")
  void givenValidCoupon_whenProcessCheckout_thenIgnoresDiscountAndProcessesWithFullAmount() {
    LocalDateTime validDate = LocalDateTime.now().plusDays(1);
    given(couponService.findCoupon("VALID_COUPON"))
        .willReturn(new Coupon("VALID_COUPON", 10.0, validDate));

    ArgumentCaptor<CheckoutOrder> orderCaptor = ArgumentCaptor.forClass(CheckoutOrder.class);

    checkoutProcessorService.processCheckout("email@email.com", 100.0, "VALID_COUPON");

    then(orderRepository).should().save(orderCaptor.capture());
    CheckoutOrder savedOrder = orderCaptor.getValue();

    assertEquals(90.0, savedOrder.getFinalAmount());
  }

  @Test
  @DisplayName("Should apply discount, save order with APPROVED status, and publish event when coupon is valid")
  void givenValidCoupon_whenProcessCheckout_thenAppliesDiscountSavesOrderAndPublishesEvent() {
    LocalDateTime futureExpiration = LocalDateTime.now().plusDays(1);
    given(couponService.findCoupon("VALID_COUPON"))
        .willReturn(new Coupon("VALID_COUPON", 10.0, futureExpiration));

    ArgumentCaptor<CheckoutOrder> orderCaptor = ArgumentCaptor.forClass(CheckoutOrder.class);

    checkoutProcessorService.processCheckout("email@email.com", 100.0, "VALID_COUPON");

    then(orderRepository).should().save(orderCaptor.capture());
    CheckoutOrder savedOrder = orderCaptor.getValue();

    assertEquals("email@email.com", savedOrder.getCustomerEmail());
    assertEquals(90.0, savedOrder.getFinalAmount());
    assertEquals("APPROVED", savedOrder.getStatus());
    assertNotNull(savedOrder.getCreatedAt());

    then(eventPublisher).should().publishCheckoutEvent(savedOrder);
  }
}
