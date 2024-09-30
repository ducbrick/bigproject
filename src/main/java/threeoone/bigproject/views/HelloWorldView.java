package threeoone.bigproject.views;

/**
 * A <code>View</code> that greets the user.
 * For demonstration purposes only.
 *
 * @author DUCBRICK
 */
public class HelloWorldView implements View {
  private String username;

  /**
   * Set the username for the <code>HelloWorldView</code> to greet when calling <code>render()</code>.
   *
   * @param username name of the user for the <code>HelloWorldView</code> to greet
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Say hello to user, whose name was set using <code>setUsername()</code>.
   */
  @Override
  public void render() {
    System.out.println("Hello " + username);
  }

  @Override
  public void stopRendering() {
  }
}
