package article_23_abstract_class;

public abstract class PaymentProcessor {

  public abstract double getDiscountPercentage();

  public double applyDiscount(double amount) {
    return amount - (amount * getDiscountPercentage());
  }
}
