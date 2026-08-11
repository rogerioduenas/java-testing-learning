package article_06_inject_parameters;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

public class PersonParameterResolver implements ParameterResolver {

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType() == Person.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
    String testMethodName = extensionContext.getRequiredTestMethod().getName();

    if (testMethodName.contains("Invalid")) {
      return new Person(null, null);
    }

    return new Person(1L, "Dev");
  }
}
