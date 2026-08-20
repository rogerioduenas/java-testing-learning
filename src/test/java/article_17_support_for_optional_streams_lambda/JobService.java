package article_17_support_for_optional_streams_lambda;

import java.util.Optional;
import java.util.stream.Stream;

public interface JobService {
  Optional<String> findJob();
  Stream<String> listJobs();
  String findJobByTitle(String title);
}
