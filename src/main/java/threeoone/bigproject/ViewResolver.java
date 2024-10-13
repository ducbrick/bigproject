package threeoone.bigproject;

import threeoone.bigproject.views.View;

/**
 * A component to process/resolve Views.
 * During the application runtime, multiple Views may be
 * opened and rendered,
 * a <code>ViewResolver</code> is responsible for handling the transition
 * from one View to another.
 *
 * @see View
 * @author DUCBRICK
 */
public interface ViewResolver {

  /**
   * Resolve an incoming <code>View</code>.
   * This method is responsible for transitioning from the previous <code>View</code>
   * to the new one.
   *
   * @param view incoming <code>View</code> to be resolved
   * @see View
   */
  public void resolveView(View view);
}
