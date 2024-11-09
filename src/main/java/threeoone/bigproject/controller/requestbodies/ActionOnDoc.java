package threeoone.bigproject.controller.requestbodies;

import threeoone.bigproject.controller.DocActionType;
import threeoone.bigproject.entities.Document;

/**
 * A request body for make an action on given document
 *
 * @param type type of action {@link DocActionType}
 * @param document document need to make action on
 */
public record ActionOnDoc(DocActionType type, Document document) {
}
