package article_14_exception_throwing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThrowingTest {

  @Mock
  MyDictionary dictionary;

  //Non-Void Return
  @Test
  void givenNonVoidReturnType_whenUsingWhenThen_thenExceptionIsThrown() {

    when(dictionary.getMeaning(anyString())).thenThrow(NullPointerException.class);

    assertThrows(NullPointerException.class, () -> dictionary.getMeaning("word"));
  }

  //Void Return
  @Test
  void givenVoidReturnType_whenUsingDoThrow_thenExceptionIsThrown() {
    doThrow(IllegalStateException.class).when(dictionary).add(anyString(), anyString());

    assertThrows(IllegalStateException.class, () -> dictionary.add("word", "meaning"));
  }

  //Exception as an Object
  @Test
  void givenNonVoidReturnType_whenUsingWhenThenAndExeceptionAsNewObject_thenExceptionIsThrown() {
    when(dictionary.getMeaning(anyString())).thenThrow(new NullPointerException("Error occurred"));

    assertThrows(NullPointerException.class, () -> dictionary.getMeaning("word"));
  }

  @Test
  void givenNonVoidReturnType_whenUsingDoThrowAndExeceptionAsNewObject_thenExceptionIsThrown() {
    doThrow(new IllegalStateException("Error occurred")).when(dictionary)
        .add(anyString(), anyString());

    assertThrows(IllegalStateException.class, () -> dictionary.add("word", "meaning"));
  }

  //spy
  @Test
  void givenSpyAndNonVoidReturnType_whenUsingWhenThen_thenExceptionIsThrown() {
    MyDictionary dict = new MyDictionary();
    MyDictionary spy = Mockito.spy(dict);
    doThrow(NullPointerException.class).when(spy).getMeaning(anyString());

    assertThrows(NullPointerException.class, () -> spy.getMeaning("word"));
  }
}
