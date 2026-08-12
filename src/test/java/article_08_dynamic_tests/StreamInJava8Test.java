package article_08_dynamic_tests;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamInJava8Test {

  @TestFactory
  Stream<DynamicTest> dynamicTestsFromStreamInJava8() {
    DomainNameResolver resolver = new DomainNameResolver();

    Map<String, String> testCases = Map.of(
        "www.somedomain.com", "154.174.10.56",
        "www.anotherdomain.com", "211.152.104.132",
        "www.yetanotherdomain.com", "178.144.120.156"
    );

    return testCases.entrySet().stream()
        .map(entry -> DynamicTest.dynamicTest(
            "Resolving: " + entry.getKey(),
            () -> assertEquals(entry.getValue(), resolver.resolveDomain(entry.getKey()))
        ));
  }

  private static class DomainNameResolver {
    public String resolveDomain(String domain) {
      return switch (domain) {
        case "www.somedomain.com" -> "154.174.10.56";
        case "www.anotherdomain.com" -> "211.152.104.132";
        case "www.yetanotherdomain.com" -> "178.144.120.156";
        default -> throw new IllegalArgumentException("Unknown domain: " + domain);
      };
    }
  }
}
