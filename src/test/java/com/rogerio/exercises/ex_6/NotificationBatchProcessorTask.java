package com.rogerio.exercises.ex_6;

import com.rogerio.exercises.ex_6.NotificationBatchProcessor;
import com.rogerio.exercises.ex_6.NotificationTask;
import com.rogerio.exercises.ex_6.TaskExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationBatchProcessorTask {

  @Mock
  private TaskExecutor executor;

  @InjectMocks
  private NotificationBatchProcessor processor;

  @Test
  @DisplayName("Should return true when task is completed by executor")
  void given_validTask_when_processTask_then_usesDoAnswerToMarkTaskCompletedAndReturnsTrue() {
    NotificationTask task = new NotificationTask("Valid message", 0);

    doAnswer(invocation -> {
      NotificationTask t = invocation.getArgument(0);
      t.markAsCompleted();
      return null;
    }).when(executor).execute(task);

    boolean result = processor.processTask(task);

    assertTrue(result);
    verify(executor).execute(task);
  }

  @ParameterizedTest
  @ValueSource(ints = {3, 4})
  @DisplayName("Should throw exception and not execute when task exceeds max retries")
  void given_taskWithExceededRetries_when_processTask_then_throwsIllegalArgumentExceptionAndDoesNotExecute(int invalidRetries) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> processor.processTask(new NotificationTask("Valid message", invalidRetries))
    );

    assertEquals("Retry count must be less than 3", exception.getMessage());
    verifyNoInteractions(executor);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"  ", "\t", "\n"})
  @DisplayName("Should throw exception when message is null or empty")
  void given_nullOrEmptyTaskMessage_when_processTask_then_throwsIllegalArgumentException(String invalidMessage) {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> processor.processTask(new NotificationTask(invalidMessage, 1))
    );

    assertEquals("Message cannot be blank!", exception.getMessage());
    verifyNoInteractions(executor);
  }

  @Test
  @DisplayName("Should pass task with correct message to executor")
  void given_validTask_when_executing_then_verifiesTaskMessageUsingArgThat() {
    NotificationTask task = new NotificationTask("Valid message", 0);

    processor.processTask(task);

    verify(executor).execute(argThat(arg -> arg.getMessage().equals("Valid message")));
  }
}
