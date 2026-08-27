package utils;

import java.util.Collection;

public final class Validate {

  private Validate() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  public static void notBlank(String value, String errorMessage) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void notNull(Object object, String errorMessage) {
    if (object == null) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void positive(double value, String errorMessage) {
    if (value <= 0) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void notNegative(double value, String errorMessage) {
    if (value < 0) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void notEmpty(Collection<?> collection, String errorMessage) {
    if (collection == null || collection.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void range(double value, double min, double max, String errorMessage) {
    if (value < min || value > max) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isFalse(boolean expression, String message) {
    if (expression) {
      throw new IllegalArgumentException(message);
    }
  }
}
