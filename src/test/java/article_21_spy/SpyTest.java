package article_21_spy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpyTest {

  @Mock
  List<String> mockList = new ArrayList<>();

  @Spy
  List<String> spyList = new ArrayList<>();

  //Simple Example
  @Test
  void givenUsingSpyMethod_whenSpyingOnList_thenCorrect() {
    List<String> list = new ArrayList<String>();
    List<String> internalSpyList = spy(list);

    internalSpyList.add("one");
    internalSpyList.add("two");

    verify(internalSpyList).add("one");
    verify(internalSpyList).add("two");

    assertEquals(2, internalSpyList.size());
  }

  //The @Spy Annotation
  @Test
  void givenUsingSpyAnnotation_whenSpyingOnList_thenCorrect() {
    spyList.add("one");
    spyList.add("two");

    verify(spyList).add("one");
    verify(spyList).add("two");

    assertEquals(2, spyList.size());
  }

  //Stubbing
  @Test
  void givenASpy_whenStubbingTheBehaviour_thenCorrect() {
    List<String> list = new ArrayList<String>();
    List<String> spyList = spy(list);

    assertEquals(0, spyList.size());

    doReturn(100).when(spyList).size();
    assertEquals(100, spyList.size());
  }

  //Mock vs Spy
  @Test
  void whenCreateMock_thenCreated() {
    mockList.add("one");

    verify(mockList).add("one");

    assertEquals(0, mockList.size());
  }

  @Test
  void whenCreateSpy_thenCreate() {
    spyList.add("one");

    verify(spyList).add("one");

    assertEquals(1, spyList.size());
  }
}
