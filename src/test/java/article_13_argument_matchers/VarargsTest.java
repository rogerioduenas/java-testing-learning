package article_13_argument_matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VarargsTest {

  @Mock
  private MessageService messageService;

  @Test
  void verifyVarargsExactlyOneArgument() {
    messageService.printTags("java");

    verify(messageService).printTags(any());
  }

  @Test
  void captureOnlySpecificSubtypesInGenerics() {
    List<String> list = List.of("item1", "item2");
    messageService.processItems(list);

    ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
    verify(messageService).processItems(captor.capture());

    messageService.printTags("java", "spring", "docker");
    verify(messageService).printTags(any(String[].class));

    assertEquals(2, captor.getValue().size());
  }
}
