package article_11_mock_spy_captor_injectMocks.inject_mocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InjectSpyTest {

  @Mock
  Map<String, String> wordMap;

  MyDictionary spyDic;

  @BeforeEach
  public void setUp() {
    spyDic = Mockito.spy(new MyDictionary(wordMap));
  }

  @Test
  public void whenUseSpyWithMock_thenCorrect() {
    Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

    assertEquals("aMeaning", spyDic.getMeaning("aWord"));
  }
}
