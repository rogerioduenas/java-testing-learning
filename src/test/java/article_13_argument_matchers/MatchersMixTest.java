package article_13_argument_matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchersMixTest {

  @Mock
  private FlowerService flowerService;

  @Test
  void error_WhenMixingExactValueWithMatcher() {
    assertThrows(InvalidUseOfMatchersException.class, () -> {
      when(flowerService.isABigFlower("poppy", anyInt())).thenReturn(true);
    });
  }

  @Test
  void success_WhenUsingEqForExactValue() {
    when(flowerService.isABigFlower(eq("poppy"), anyInt())).thenReturn(true);

    assertTrue(flowerService.isABigFlower("poppy", 20));
  }
}
