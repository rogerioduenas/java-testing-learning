package article_17_support_for_optional_streams_lambda;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class OptionalAndStreamTest {

  @Mock
  private JobService jobService;

  @Test
  void shouldReturnEmptyOptionalByDefault() {
    Optional<String> result = jobService.findJob();

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnEmptyStreamByDefault() {
    Stream<String> result = jobService.listJobs();

    assertTrue(result.toList().isEmpty());
  }
}
