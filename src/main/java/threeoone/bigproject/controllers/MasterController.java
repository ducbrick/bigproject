package threeoone.bigproject.controllers;

import threeoone.bigproject.controllers.helloworldcontroller.HelloWorldController;

/**
 * The Controller with every API of the system.
 * A Controller is a component with one or multiple APIs that,
 * when called with some required inputs,
 * returns a specific <code>View</code> populated with data.
 * A <code>MasterController</code> has no APIs of its own,
 * but extends every other Controllers and inherits their APIs.
 * Essentially, a <code>MasterController</code> gives other components
 * easy access to any of the system's API.
 *
 * @author DUCBRICK
 */
public interface MasterController extends HelloWorldController {
}
