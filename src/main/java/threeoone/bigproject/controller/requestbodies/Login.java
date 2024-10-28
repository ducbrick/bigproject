package threeoone.bigproject.controller.requestbodies;

/**
 * A request body holds user's information which is sent from login page to service
 * @param username: username of user
 * @param password: password of user
 */
public record Login (String username, String password){
}
