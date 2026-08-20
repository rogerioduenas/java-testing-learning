package article_19_strict_stubbing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StubbingTest {

  @Mock
  MyList myList;

  @Test
  public void givenUnusedStub_whenInvokingGetThenThrowUnnecessaryStubbingException() {
    when(myList.add("one")).thenReturn(true); // this won't get called
    when(myList.get(anyInt())).thenReturn("hello");
    assertEquals("hello", myList.get(1));
  }

  @Test
  public void givenLenientdStub_whenInvokingGetThenThrowUnnecessaryStubbingException() {
    lenient().when(myList.add("one")).thenReturn(true);
    when(myList.get(anyInt())).thenReturn("hello");
    assertEquals("hello", myList.get(1));
  }
}
