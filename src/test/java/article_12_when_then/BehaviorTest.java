package article_12_when_then;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BehaviorTest {

  @Test
  public void whenConfigureReturnBehavior_thenCorrect() {
    MyList listMock = mock(MyList.class);
    when(listMock.add(anyString())).thenReturn(false);

    boolean added = listMock.add("testWord");

    assertFalse(added);
  }

  @Test
  public void whenConfigureAlternativeReturnBehavior_thenCorrect() {
    MyList listMock = mock(MyList.class);
    doReturn(false).when(listMock).add(anyString());

    boolean added = listMock.add("testWord");

    assertFalse(added);
  }

  @Test
  public void whenConfigureThrowException_thenCorrect() {
    MyList listMock = mock(MyList.class);
    when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

    assertThrows(IllegalStateException.class, () -> listMock.add("testWord"));
  }

  @Test
  public void whenConfigureVoidMethodThrowException_thenCorrect() {
    MyList listMock = mock(MyList.class);
    doThrow(NullPointerException.class).when(listMock).clear();

    assertThrows(NullPointerException.class, () -> listMock.clear());
  }

  @Test
  public void whenConfigureMultipleCalls_thenCorrect() {
    MyList listMock = mock(MyList.class);
    when(listMock.add(anyString()))
        .thenReturn(false)
        .thenThrow(IllegalStateException.class);

    assertFalse(listMock.add("firstCall"));
    assertThrows(IllegalStateException.class, () -> listMock.add("secondCall"));
  }

  @Test
  public void whenConfigureSpyBehavior_thenCorrect() {
    MyList instance = new MyList();
    MyList spyList = spy(instance);

    doThrow(NullPointerException.class).when(spyList).size();

    assertThrows(NullPointerException.class, () -> spyList.size());
  }

  @Test
  public void whenCallRealMethodOnMock_thenCorrect() {
    MyList listMock = mock(MyList.class);
    when(listMock.size()).thenCallRealMethod();

    assertEquals(1, listMock.size());
  }

  @Test
  public void whenConfigureCustomAnswer_thenCorrect() {
    MyList listMock = mock(MyList.class);
    doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

    String element = listMock.get(1);

    assertEquals("Always the same", element);
  }
}
