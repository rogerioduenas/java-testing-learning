package article_11_mock_spy_captor_injectMocks.inject_mocks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InjectSpyWithoutSetupTest {

  @Mock
  Map<String, String> wordMap;

  @Spy
  @InjectMocks
  MyDictionary spyDic;

  @Test
  public void testSpyWithInjectedMock() {
    Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

    assertEquals("aMeaning", spyDic.getMeaning("aWord"));
  }
}
