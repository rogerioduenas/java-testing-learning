package com.rogerio.exercises.ex_6;

import utils.Validate;

public class NotificationTask {
  private final String message;
  private int retryCount;
  private boolean completed;

  public NotificationTask(String message, int retryCount) {
    validateInputs(message, retryCount);
    this.message = message;
    this.retryCount = retryCount;
    this.completed = false;
  }

  private void validateInputs(String message, int retryCount) {
    Validate.notBlank(message, "Message cannot be blank!");
    if (retryCount >= 3){
      throw new IllegalArgumentException("Retry count must be less than 3");
    }
  }

  public String getMessage() { return message; }
  public int getRetryCount() { return retryCount; }
  public boolean isCompleted() { return completed; }
  public void markAsCompleted() { this.completed = true; }
}
