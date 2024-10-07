package threeoone.bigproject.controller;

/**
 * A component that exposes multiple methods (dubbed "APIs") for other components to call
 * (dubbed "making requests).
 *
 * @author DUCBRICK, HUY1902
 *
 */
public interface Controller{
  /**
   * Requests to greet user with user's information as parameter.
   *
   * @param userInformation the user's information
   */
  public void helloWorld(UserInformation userInformation);

  /**
   * Requests to obtain user's information.
   */
  public void getUserInfo();

  /**
   * Request to add document
   */
  public void addDoc();
}