package threeoone.bigproject.util;

import javafx.scene.control.Alert;

/**
 * Util class for alerts create or showing
 */
public class Alerts {
  /**
   * Create new confirmation alert with given title and message,
   * header is set to be empty
   *
   * @param title alert title
   * @param message alert message
   * @return new alert with given param
   */
  public static Alert alertConfirmation(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    return alert;
  }
}
