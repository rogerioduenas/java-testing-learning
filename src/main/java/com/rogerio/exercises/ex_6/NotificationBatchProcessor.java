package com.rogerio.exercises.ex_6;

import utils.Validate;

public class NotificationBatchProcessor {

  private final TaskExecutor executor;

  public NotificationBatchProcessor(TaskExecutor executor) {
    this.executor = executor;
  }

  public boolean processTask(NotificationTask task) {
    Validate.notNull(task, "Task cannot be null!");

    executor.execute(task);

    return task.isCompleted();
  }
}
