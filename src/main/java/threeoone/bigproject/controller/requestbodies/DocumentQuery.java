package threeoone.bigproject.controller.requestbodies;

import threeoone.bigproject.controller.viewcontrollers.DocQueryType;
import threeoone.bigproject.entities.Document;

/**
 * The {@code DocumentQuery} contains a query type and a document.
 * It is used to specify the type of document query being performed and the document involved.
 *
 * @author HUY1902
 */
public record DocumentQuery(DocQueryType type, Document document) {
}
