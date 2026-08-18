package article_14_exception_throwing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Throwing {

  @Test
  void givenNonVoidReturnType_whenUsingWhenThen_thenExceptionIsThrown() {

    MyDictionary dictMock = mock(MyDictionary.class);

    when(dictMock.getMeaning(anyString())).thenThrow(NullPointerException.class);

    assertThrows(NullPointerException.class, () -> dictMock.getMeaning("word"));
  }
}
