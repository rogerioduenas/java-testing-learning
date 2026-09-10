package com.rogerio.exercises.ex_9;

import utils.Validate;

import java.time.LocalDateTime;

public class CheckoutProcessorService {

  private final FraudCheckService fraudCheckService;
  private final CouponService couponService;
  private final OrderRepository orderRepository;
  private final EventPublisher eventPublisher;

  public CheckoutProcessorService(FraudCheckService fraudCheckService, CouponService couponService,
                                  OrderRepository orderRepository, EventPublisher eventPublisher) {
    this.fraudCheckService = fraudCheckService;
    this.couponService = couponService;
    this.orderRepository = orderRepository;
    this.eventPublisher = eventPublisher;
  }

  public void processCheckout(String email, double rawAmount, String couponCode) {
    validateInputs(email, rawAmount, couponCode);
    checkFraudRisk(email);

    double discount = calculateDiscount(couponCode);
    double finalAmount = rawAmount - (rawAmount * (discount / 100.0));

    CheckoutOrder order = new CheckoutOrder(email, finalAmount, LocalDateTime.now(), "APPROVED");
    orderRepository.save(order);
    eventPublisher.publishCheckoutEvent(order);
  }

  private void validateInputs(String email, double rawAmount, String couponCode) {
    Validate.notBlank(email, "Email address is invalid");
    Validate.positive(rawAmount, "RawAmount must be greater than zero");
    Validate.notBlank(couponCode, "Coupon code can't be empty or null");
  }

  private void checkFraudRisk(String email) {
    if (fraudCheckService.isHighRisk(email)) {
      throw new IllegalStateException("Customer is high risk, transaction wasn't completed");
    }
  }

  private double calculateDiscount(String couponCode) {
    Coupon coupon = couponService.findCoupon(couponCode);
    if (coupon != null && !LocalDateTime.now().isAfter(coupon.getExpirationDate())) {
      return coupon.getDiscountPercentage();
    }
    return 0.0;
  }
}
