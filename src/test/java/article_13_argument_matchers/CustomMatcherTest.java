package article_13_argument_matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomMatcherTest {

  @Mock
  private MessageService messageService;

  record MessageMatcher(String expectedText) implements ArgumentMatcher<Message> {

    @Override
    public boolean matches(Message message) {
      return message != null && expectedText.equals(message.getText());
    }
  }

  @Test
  void validatesInternalFieldsWithCustomMatcher() {
    Message msg = new Message("Ana", "Bob", "Hello");
    messageService.deliverMessage(msg);

    verify(messageService).deliverMessage(argThat(new MessageMatcher("Hello")));
  }
}
