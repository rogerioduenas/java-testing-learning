package article_15_verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerifyTest {

  @Mock
  MyList mockedList;

  @Test
  void whenSizeIsCalled_thenVerifyInteraction(){
    mockedList.size();

    verify(mockedList).size();
  }

  @Test
  void whenSizeIsCalled_thenVerifyNumberOfInteractions(){
    mockedList.size();

    verify(mockedList, times(1)).size();
  }

  @Test
  void whenNoInteractionsOccurred_thenVerifyNoInteractions() {
    verifyNoInteractions(mockedList);
  }

  @Test
  void whenMethodNotCalled_thenVerifyZeroInteractions() {
    verify(mockedList, times(0)).size();
  }

  @Test
  void whenUnexpectedInteractionsExist_thenThrowNoInteractionsWanted() {
    mockedList.size();
    mockedList.clear();

    verify(mockedList).size();
    assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mockedList));
  }

  @Test
  void whenMultipleMethodsCalled_thenVerifyOrder() {
    mockedList.size();
    mockedList.add("a parameter");
    mockedList.clear();

    InOrder inOrder = Mockito.inOrder(mockedList);

    inOrder.verify(mockedList).size();
    inOrder.verify(mockedList).add("a parameter");
    inOrder.verify(mockedList).clear();
  }

  @Test
  void whenMethodNeverCalled_thenVerifyNever() {
    mockedList.size();

    verify(mockedList, never()).clear();
  }

  @Test
  void whenMethodCalledMultipleTimes_thenVerifyAtLeastAndAtMost() {
    mockedList.clear();
    mockedList.clear();
    mockedList.clear();

    verify(mockedList, atLeast(1)).clear();
    verify(mockedList, atMost(3)).clear();
  }

  @Test
  void whenExactArgumentPassed_thenVerifyInteraction() {
    mockedList.add("test");

    verify(mockedList).add("test");
  }

  @Test
  void whenFlexibleArgumentPassed_thenVerifyWithMatcher() {
    mockedList.add("test");

    verify(mockedList).add(anyString());
  }

  @Test
  void whenArgumentCaptured_thenVerifyAndAssertValue() {
    mockedList.add("Java");

    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(mockedList).add(captor.capture());

    String value = captor.getValue();
    assertEquals("Java", value);
  }
}
