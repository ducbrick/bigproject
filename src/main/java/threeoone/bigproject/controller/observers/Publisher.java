package threeoone.bigproject.controller.observers;

/**
 * The {@code Publisher} interface defines the methods required for managing
 * subscribers in a publish-subscribe pattern. Implementing classes are
 * responsible for maintaining a list of subscribers and notifying them
 * of any changes or updates.
 */
public interface Publisher {

  /**
   * Subscribes the specified subscriber to this publisher for a given data type.
   *
   * @param type the data type for which the subscriber wants to subscribe
   * @param subscriber the subscriber to be added
   */
  void subscribe(DataType type, Subscriber subscriber);

  /**
   * Unsubscribes the specified subscriber from this publisher for a given data type.
   *
   * @param type the data type for which the subscriber wants to unsubscribe
   * @param subscriber the subscriber to be removed
   */
  void unsubscribe(DataType type, Subscriber subscriber);

  /**
   * Notifies all subscribed subscribers of an update or change for a given data type.
   *
   * @param type the data type for which to notify subscribers
   * @param data the data to be passed to the subscribers
   */
  void notifySubscribers(DataType type, Object data);
}
