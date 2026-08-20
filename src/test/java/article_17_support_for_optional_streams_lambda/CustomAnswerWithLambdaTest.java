package article_17_support_for_optional_streams_lambda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomAnswerWithLambdaTest {

  @Mock
  JobService jobService;

  @Test
  void shouldReturnCustomAnswerWithLambda() {
    when(jobService.findJobByTitle(anyString())).then(invocation -> {
      String argument = invocation.getArgument(0);
      return argument.equals("Peter") ? "Parker" : "unknown";
    });

    assertEquals("Parker", jobService.findJobByTitle("Peter"));
    assertEquals("unknown", jobService.findJobByTitle("John"));
  }
}
