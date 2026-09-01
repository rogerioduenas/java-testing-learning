package com.rogerio.exercises.ex_3;

public interface AuditLogger {
  void logTransfer(long fromId, long toId, double amount);
}
