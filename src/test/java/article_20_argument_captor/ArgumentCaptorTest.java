package article_20_argument_captor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArgumentCaptorTest {

  @Captor
  private ArgumentCaptor<String> captor;

  @Mock
  private JobService jobService;

  @Test
  void shouldCaptureArgument(){
    jobService.findJobByTitle("title");

    verify(jobService).findJobByTitle(captor.capture());

    assertEquals("title", captor.getValue());
  }

  @Test
  void shouldAvoidCaptorInWhenAndUseMatcherInstead() {
    // ❌ BAD practice: Using ArgumentCaptor in the `when` clause (makes the test confusing and hides errors)
    // when(jobService.findJobByTitle(captor.capture())).thenReturn("Developer");

    // ✅ CORRECT way: Use ArgumentMatchers in `when` for stubbing.
    when(jobService.findJobByTitle(eq("Java"))).thenReturn("Developer");

    String result = jobService.findJobByTitle("Java");

    assertEquals("Developer", result);

    // ArgumentCaptor is reserved ONLY for verification (verify), if necessary.
    verify(jobService).findJobByTitle(captor.capture());
    assertEquals("Java", captor.getValue());
  }
}
