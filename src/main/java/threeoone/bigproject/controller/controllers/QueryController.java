package threeoone.bigproject.controller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.AddNewDocController;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.services.GoogleAPIService;

/**
 * Controller class for handling book queries
 *
 * @author HUY1902
 */
@Component
public class QueryController {
  private final AddNewDocController addNewDocController;
  private final GoogleAPIService googleAPIService;

  /**
   * Autowired constructor with necessary dependencies.
   *
   * @param addNewDocController           controller for adding new documents
   * @param googleAPIService              service for searching books by ISBN
   * @param queryISBNGoogleRequestSender  request sender for querying Google Books API by ISBN
   */
  @Autowired
  public QueryController(AddNewDocController addNewDocController,
                         GoogleAPIService googleAPIService,
                         RequestSender<String> queryISBNGoogleRequestSender) {
    this.addNewDocController = addNewDocController;
    this.googleAPIService = googleAPIService;
  }

  /**
   * Registers the request sender for handling ISBN queries.
   *
   * @param queryISBNGoogleRequestSender the request sender to be registered
   */
  @Autowired
  public void registerRequestSender(RequestSender<String> queryISBNGoogleRequestSender) {
    queryISBNGoogleRequestSender.registerReceiver(this::getQueryAndFulFill);
  }

  /**
   * Queries through {@link GoogleAPIService} then gives information about the book back to {@link AddNewDocController}.
   *
   * @param isbn the ISBN to query
   */
  public void getQueryAndFulFill(String isbn) {
    Document result = googleAPIService.findBookByISBN(isbn);
    if (result != null) {
      addNewDocController.fulfillInfo(result);
    }
  }
}
