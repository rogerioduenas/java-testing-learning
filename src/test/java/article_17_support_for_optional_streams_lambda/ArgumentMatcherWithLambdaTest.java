package article_17_support_for_optional_streams_lambda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArgumentMatcherWithLambdaTest {

  @Mock
  JobService jobService;

  @Test
  void shouldVerifyArgumentUsingLambdaMatcher() {
    when(jobService.findJobByTitle(argThat(title -> title.startsWith("Java")))).thenReturn("Developer");

    String result = jobService.findJobByTitle("Java Senior");

    assertEquals("Developer", result);
  }
}
