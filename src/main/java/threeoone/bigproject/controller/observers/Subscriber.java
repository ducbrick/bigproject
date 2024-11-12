package threeoone.bigproject.controller.observers;

/**
 * The {@code Subscriber} interface defines a method for receiving updates from a {@link Publisher}.
 * Implementing classes should define specific behavior for the {@code update} method to handle updates.
 *
 * @author HUY1902
 */
public interface Subscriber {

  /**
   * This method is called by the {@code Publisher} to notify the {@code Subscriber} of an update.
   *
   * @param data the data notified by {@link Publisher}
   */
  void update(Object data);
}
