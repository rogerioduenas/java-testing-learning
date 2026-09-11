package com.rogerio.exercises.ex_10.service;

public interface AuditService {
  void log(long orderId, String event);
}
