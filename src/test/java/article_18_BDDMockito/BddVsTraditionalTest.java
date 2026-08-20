package article_18_BDDMockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BddVsTraditionalTest {

  @Mock
  private JobService jobService;

  @Test
  void traditionalMockito() {
    when(jobService.findJobByTitle("Java")).thenReturn("Developer");

    String result = jobService.findJobByTitle("Java");

    assertEquals("Developer", result);
    verify(jobService).findJobByTitle("Java");
  }

  @Test
  void bddMockito() {
    given(jobService.findJobByTitle("Java")).willReturn("Developer");

    String result = jobService.findJobByTitle("Java");

    assertEquals("Developer", result);
    then(jobService).should().findJobByTitle("Java");
  }
}
