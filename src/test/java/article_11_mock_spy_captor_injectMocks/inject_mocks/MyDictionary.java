package article_11_mock_spy_captor_injectMocks.inject_mocks;

import java.util.Map;

public class MyDictionary {
  Map<String, String> wordMap;

  public MyDictionary(Map<String, String> wordMap) {
    this.wordMap = wordMap;
  }

  public String getMeaning(final String word) {
    return wordMap.get(word);
  }
}

