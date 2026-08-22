package article_23_abstract_class;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorTest {

  @Test
  void shouldApplyDiscountCorrectly() {
    PaymentProcessor processor = Mockito.mock(
        PaymentProcessor.class,
        Mockito.CALLS_REAL_METHODS
    );

    Mockito.when(processor.getDiscountPercentage()).thenReturn(0.10);

    double result = processor.applyDiscount(100.0);

    assertEquals(90.0, result);
  }
}
