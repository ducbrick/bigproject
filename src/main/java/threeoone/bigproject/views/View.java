package threeoone.bigproject.views;

import threeoone.bigproject.controllers.MasterController;

/**
 * A component that acts as an interface for user interaction.
 * A <code>View</code> can render data to the user, or be required to pause/stop rendering.
 * During a {@code View} rendering time, it may make requests to Controllers.
 * Stopping the rendering process requires a {@code View} reset its internal properties to their
 * original values, free up resources, or pause/stop any of its running threads/tasks.
 *
 * @author DUCBRICK
 */
public interface View {

  /**
   * Renders data and interactable components to user and waits to make any requests.
   *
   * @param controller a {@code MasterController} to give this {@code View} access to other APIs
   */
  public void render(MasterController controller);

  /**
   * Stop rendering the <code>View</code> and reset its properties or pause/stop tasks as required.
   */
  public void stopRendering();
}
