package threeoone.bigproject.controller.observers;

import org.springframework.stereotype.Component;
import java.util.HashMap;

/**
 * The {@code QueryPublisher} class implements the {@link Publisher} interface
 * and manages subscribers in a publish-subscribe pattern. It maintains a list of
 * subscribers and notifies them of updates or changes based on the specified data type.
 *
 * @author HUY1902
 */
@Component
public class QueryPublisher implements Publisher {
  private final HashMap<DataType, Subscriber> subscribers = new HashMap<>();

  /**
   * Subscribes the specified subscriber to this publisher for a given data type.
   *
   * @param type       the data type for which the subscriber wants to subscribe
   * @param subscriber the subscriber to be added
   */
  @Override
  public void subscribe(DataType type, Subscriber subscriber) {
    subscribers.put(type, subscriber);
  }

  /**
   * Unsubscribes the specified subscriber from this publisher for a given data type.
   *
   * @param type       the data type for which the subscriber wants to unsubscribe
   * @param subscriber the subscriber to be removed
   */
  @Override
  public void unsubscribe(DataType type, Subscriber subscriber) {
    subscribers.remove(type);
  }

  /**
   * Notifies all subscribed subscribers of an update or change for a given data type.
   *
   * @param type the data type for which to notify subscribers
   * @param data the data to be passed to the subscribers
   */
  @Override
  public void notifySubscribers(DataType type, Object data) {
    if (subscribers.containsKey(type)) {
      subscribers.get(type).update(data);
    }
  }
}
