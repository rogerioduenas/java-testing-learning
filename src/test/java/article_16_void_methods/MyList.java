package article_16_void_methods;

import java.util.AbstractList;

public class MyList extends AbstractList<String> {

  @Override
  public String get(int index) {
    return "";
  }

  @Override
  public void add(int index, String element) {
    // no-op
  }

  @Override
  public int size() {
    return 0;
  }
}
