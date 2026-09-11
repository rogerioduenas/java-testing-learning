package com.rogerio.exercises.ex_10.service;

import com.rogerio.exercises.ex_10.exception.InsufficientStockException;
import com.rogerio.exercises.ex_10.model.PaymentResult;
import com.rogerio.exercises.ex_10.model.PaymentTransaction;
import com.rogerio.exercises.ex_10.repository.TransactionRepository;
import utils.Validate;

public class PaymentOrderService {

  private final StockService stockService;
  private final PaymentGateway paymentGateway;
  private final TransactionRepository transactionRepository;
  private final StockReservationService stockReservationService;
  private final DeliveryService deliveryService;
  private final AuditService auditService;

  public PaymentOrderService(StockService stockService, PaymentGateway paymentGateway, TransactionRepository transactionRepository, StockReservationService stockReservationService, DeliveryService deliveryService, AuditService auditService) {

    this.stockService = stockService;
    this.paymentGateway = paymentGateway;
    this.transactionRepository = transactionRepository;
    this.stockReservationService = stockReservationService;
    this.deliveryService = deliveryService;
    this.auditService = auditService;
  }

  public PaymentResult processOrder(long orderId, String customerEmail, String productId, int quantity, double amount) {
    validateInputs(orderId, customerEmail, productId, quantity, amount);
    validateStock(productId, quantity);

    PaymentResult result = paymentGateway.processPayment(orderId, customerEmail, amount);

    PaymentTransaction transaction = new PaymentTransaction(orderId, customerEmail, amount, "PAID");

    transactionRepository.save(transaction);

    stockReservationService.reserve(productId, quantity, orderId);

    deliveryService.dispatch(orderId);

    auditService.log(orderId, "PAYMENT_COMPLETED");

    return result;
  }

  private void validateInputs(long orderId, String customerEmail, String productId, int quantity, double amount) {
    Validate.positive(orderId, "Order ID must be greater than zero");
    Validate.notBlank(customerEmail, "Customer email must be not blank or null");
    Validate.notBlank(productId, "Product ID must be not blank or null");
    Validate.positive(quantity, "Quantity must be greater than zero");
    Validate.positive(amount, "Amount must be greater than zero");
  }

  private void validateStock(String productId, int quantity) {
    int available = stockService.getAvailableQuantity(productId);
    if (available < quantity) {
      throw new InsufficientStockException("Insufficient stock for product: " + productId);
    }
  }
}