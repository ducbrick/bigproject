package threeoone.bigproject.controller.controllers;

import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.MemberQueryType;
import threeoone.bigproject.controller.requestbodies.DocumentQuery;
import threeoone.bigproject.controller.requestbodies.MemberQuery;
import threeoone.bigproject.controller.viewcontrollers.AddNewDocController;
import threeoone.bigproject.controller.viewcontrollers.DocQueryType;
import threeoone.bigproject.controller.viewcontrollers.DocSearchBarController;
import threeoone.bigproject.controller.viewcontrollers.MemberSearchBarController;
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
                                    RequestSender<MemberQuery> memberQueryRequestSender,
                                    RequestSender<DocumentQuery> documentQueryRequestSender) {
    queryISBNGoogleRequestSender.registerReceiver(this::getQueryAndFulFill);
    memberQueryRequestSender.registerReceiver(this::respondMemberQuery);
    documentQueryRequestSender.registerReceiver(this::respondDocQuery);
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
   * Responds to a user query by fetching member data from {@link #memberRetrievalService}
   * based on the query type and setting the results in the {@link #memberSearchBarController}.
   *
   * @param memberQuery the {@link MemberQuery} containing the
   *                   {@link MemberQueryType} and the member information {@link Member}
   */
  public void respondMemberQuery(MemberQuery memberQuery) {
    switch (memberQuery.type()) {
      case MemberQueryType.USER_NAME -> {
        List<Member> result = memberRetrievalService.findWhoseNamesContain(memberQuery.member().getName());
        if (result.isEmpty()) {
          break;
        }
        memberSearchBarController.setResult(FXCollections.observableArrayList(result));
      }
      case MemberQueryType.USER_ID -> {
        Member result = memberRetrievalService.findById(memberQuery.member().getId());
        if (result == null) {
          break;
        }
        memberSearchBarController.setResult(FXCollections.observableArrayList(result));
      }
    }
  }

  /**
   * Responds to a user query by fetching document data from {@link #documentRetrievalService}
   * based on the query type and setting the results in the {@link #docSearchBarController}.
   *
   * @param documentQuery the {@link DocumentQuery} containing the
   *                   {@link DocQueryType} and the document information {@link Document}
   */
  public void respondDocQuery(DocumentQuery documentQuery) {
    switch (documentQuery.type()) {
      case DocQueryType.ID -> {
        Document result = documentRetrievalService.getDocumentById(documentQuery.document().getId());
        if (result == null) {
          break;
        }
        docSearchBarController.setResult(FXCollections.observableArrayList(result));
      }
    }
  }
}
