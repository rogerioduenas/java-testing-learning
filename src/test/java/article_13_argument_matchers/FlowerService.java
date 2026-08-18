package article_13_argument_matchers;

import java.util.List;

class FlowerService {

  public boolean isABigFlower(String name, int size) {
    return size > 10;
  }
}

class Message {
  private String from, to, text;

  public Message(String from, String to, String text) {
    this.from = from;
    this.to = to;
    this.text = text;
  }

  public String getText() {
    return text;
  }
}

class MessageService {
  public void deliverMessage(Message message) {
  }

  public void printTags(String... tags) {
  }

  public void processItems(List<String> items) {
  }
}
