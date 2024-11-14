package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.viewcontrollers.*;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.services.DocumentRetrievalService;
import threeoone.bigproject.services.GoogleAPIService;
import threeoone.bigproject.services.MemberRetrievalService;

import java.util.List;

/**
 * Controller class for handling queries (contain {@link Document} and {@link  Member})
 *
 * @author HUY1902
 */
@Component
@RequiredArgsConstructor
public class QueryController {
  private final AddNewDocController addNewDocController;
  private final GoogleAPIService googleAPIService;
  private final MemberRetrievalService memberRetrievalService;
  private final MemberSearchBarController memberSearchBarController;
  private final DocumentRetrievalService documentRetrievalService;
  private final DocSearchBarController docSearchBarController;

  /**
   * Registers the request sender for handling ISBN queries.
   *
   * @param queryISBNGoogleRequestSender the request sender to be registered
   */
  @Autowired
  public void registerRequestSender(RequestSender<String> queryISBNGoogleRequestSender,
                                    RequestSender<String> queryDocByNameRequestSender,
                                    RequestSender<String> queryDocByAuthorRequestSender,
                                    RequestSender<String> queryDocByCategoryRequestSender,
                                    RequestSender<Integer> queryDocByIdRequestSender,
                                    RequestSender<String> queryMemByNameRequestSender,
                                    RequestSender<Integer> queryMemByIdRequestSender) {
    queryISBNGoogleRequestSender.registerReceiver(this::getQueryAndFulFill);
    queryDocByIdRequestSender.registerReceiver(this::queryDocByID);
    queryDocByNameRequestSender.registerReceiver(this::queryDocByName);
    queryDocByAuthorRequestSender.registerReceiver(this::queryDocByAuthor);
    queryDocByCategoryRequestSender.registerReceiver(this::queryDocByCategory);
    queryMemByNameRequestSender.registerReceiver(this::queryMemByName);
    queryMemByIdRequestSender.registerReceiver(this::queryMemById);
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

  /**
   * Queries a member by their ID and sets the result in the member search bar controller.
   *
   * @param id the ID of the member to be queried
   */
  private void queryMemById(Integer id) {
    Member result = memberRetrievalService.findById(id);
    if (result == null) {
      return;
    }
    memberSearchBarController.setResult(FXCollections.observableArrayList(result));
  }

  /**
   * Queries members by their name and sets the results in the member search bar controller.
   *
   * @param name the name of the member(s) to be queried
   */
  private void queryMemByName(String name) {
    List<Member> result = memberRetrievalService.findWhoseNamesContain(name);
    if (result.isEmpty()) {
      return;
    }
    memberSearchBarController.setResult(FXCollections.observableArrayList(result));
  }


  /**
   * Queries a document by its ID and sets it in the menu controller.
   *
   * @param id the ID of the document to be queried
   */
  private void queryDocByID(Integer id) {
    Document result = documentRetrievalService.getDocumentById(id);
    if (result == null) {
      return;
    }
    docSearchBarController.setResult(FXCollections.observableArrayList(result));
  }

  /**
   * Queries a document by its name.
   *
   * @param name the name of the document to be queried
   */
  private void queryDocByName(String name) {
    // TODO: Implement the logic to query document by name
  }

  /**
   * Queries a document by its author.
   *
   * @param author the author of the document to be queried
   */
  private void queryDocByAuthor(String author) {
    // TODO: Implement the logic to query document by author
  }

  /**
   * Queries a document by its category.
   *
   * @param category the category of the document to be queried
   */
  private void queryDocByCategory(String category) {
    // TODO: Implement the logic to query document by category
  }


}
