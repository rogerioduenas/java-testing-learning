package article_05_order_of_tests.class_order.custom_class_order;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

import java.util.Comparator;

public class CustomClassOrder implements ClassOrderer {
  @Override
  public void orderClasses(ClassOrdererContext context) {
    context.getClassDescriptors().sort(
        Comparator.comparingInt(descriptor ->
            descriptor.getTestClass().getSimpleName().length()
        )
    );
  }
}
