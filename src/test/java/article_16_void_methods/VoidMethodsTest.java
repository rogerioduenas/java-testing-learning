package article_16_void_methods;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoidMethodsTest {

  @Mock
  MyList myList;

  @Mock
  Greeting greeting;

  @Test
  public void whenAddCalled_thenVerified() {
    doNothing().when(myList).add(isA(Integer.class), isA(String.class));
    myList.add(0, "");

    verify(myList).add(0, "");
  }

  @Test
  void whenAddIsCalled_thenVerified() {
    myList.add(0, "");

    verify(myList).add(0, "");
  }

  @Test
  void givenNull_whenAddCalled_thenThrowsException() {
    assertThrows(Exception.class, () -> {
      doThrow().when(myList).add(isA(Integer.class), isNull());
    });

    myList.add(0, null);
  }

  @Test
  void givenArgumentCaptor_whenAddCalled_thenValueCaptured() {

    ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
    doNothing().when(myList).add(any(Integer.class), valueCapture.capture());

    myList.add(0, "captured");

    assertEquals("captured", valueCapture.getValue());
  }

  @Test
  void givenDoAnswer_whenAddCalled_thenAnswered() {

    doAnswer(invocation -> {
      Object arg0 = invocation.getArgument(0);
      Object arg1 = invocation.getArgument(1);

      assertEquals(3, arg0);
      assertEquals("answer me", arg1);
      return null;
    }).when(myList).add(any(Integer.class), any(String.class));

    myList.add(3, "answer me");
  }

  @Test
  void whenDoCallRealMethodOnMock_thenRealMethodCalled() {
    doCallRealMethod().when(greeting).sayHello(any(String.class));

    greeting.sayHello("Tom");

    verify(greeting).sayHello("Tom");
  }
}
