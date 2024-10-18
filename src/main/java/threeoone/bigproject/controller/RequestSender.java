package threeoone.bigproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RequestSender <T> {
  private final List <Consumer <T>> receivers = new ArrayList <> ();

  public void registerReceiver(Consumer <T> receiver) {
    receivers.add(receiver);
  }

  public void send(T requestBody) {
    for (Consumer <T> receiver : receivers) {
      receiver.accept(requestBody);
    }
  }
}
