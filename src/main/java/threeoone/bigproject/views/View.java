package threeoone.bigproject.views;

/**
 * A component that acts as an interface for user interaction.
 * A <code>View</code> can render data to the user, or be required to pause/stop rendering.
 * By stopping the rendering process, a <code>View</code> may reset its internal properties to their
 * original values, free up resources, or pause/stop any of its running threads/tasks.
 *
 * @author DUCBRICK
 */
public interface View {

  /**
   * Renders data and interactable components to user.
   */
  public void render();

  /**
   * Stop rendering the <code>View</code> and reset its properties or pause/stop tasks as required.
   */
  public void stopRendering();
}
