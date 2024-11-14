package threeoone.bigproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.controller.RequestSender;
import threeoone.bigproject.controller.requestbodies.*;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;

/**
 * {@code Spring} configuration class.
 *
 * @author DUCBRICK
 */
@Configuration
public class SpringConfig {

  /**
   * Register a {@link RequestSender} of type {@code switchSceneRequest} into {@code Spring} context.
   * Send a request to switch to a different scene.
   * The request is sent with the name of the scene to switch to.
   * Request body is {@link SwitchScene} which hold name of scene{@link threeoone.bigproject.controller.SceneName}
   *
   * @return the {@link RequestSender} to be registered
   **/
  @Bean
  public RequestSender<SwitchScene> switchSceneRequestSender() {
    return new RequestSender<>();
  }


  /*************************************************************************
   *  All request sender for login and register
   ***************************************************************************/

  /**
   * Register a {@link RequestSender} of type {@code loginRequest} into {@code Spring} context.
   * Send a LoginRequest which holds {@link User} from 'LoginPage' to service.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<User> loginRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Register a {@link RequestSender} of type {@code registerRequest} into {@code Spring} context.
   * Send a registerRequest which holds {@link User} from 'RegisterPage' to service.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<User> registerRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a request to save new lending
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<LendingDetail> saveNewLending() {
    return new RequestSender<>();
  }


  /*************************************************************************
   *  All request sender for document handler: edit, remove, borrow,... (not contain query)
   ***************************************************************************/

  /**
   * Send a request to update available action on a specific document
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> updateDocActionRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a request to get all Document in database
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<User> getListAllDocumentRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a new change on given document request to service
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> commitChangeDocRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Register a {@link RequestSender} of type {@code documentRequest} into {@code Spring} context.
   * Send Document entities {@link Document} from view to another view which need Document.
   * For example, {@link threeoone.bigproject.controller.viewcontrollers.DocumentDetailController}
   * need to know what Document user click on in
   * {@link threeoone.bigproject.controller.viewcontrollers.DocOverviewController} to show them.
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> documentDetailRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a addDocument Request which holds {@link Document} from 'AddNewDocument' page
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Document> addDocumentRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending edit document requests.
   *
   * @return a new {@code RequestSender} instance for {@code Document}
   */
  @Bean
  public RequestSender<Document> editDocumentRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending remove document requests.
   *
   * @return a new {@code RequestSender} instance for {@code Document}
   */
  @Bean
  public RequestSender<Document> removeDocumentRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending borrow document requests.
   *
   * @return a new {@code RequestSender} instance for {@code Document}
   */
  @Bean
  public RequestSender<Document> borrowDocumentRequestSender() {
    return new RequestSender<>();
  }


  /*************************************************************************
   *  All request sender for member handler: edit, remove, add, ... (not contains query)
   ***************************************************************************/

  /**
   * Send a request to service to get all member
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Member> getAllMembersRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending edit member requests.
   *
   * @return a new {@code RequestSender} instance for {@code Member}
   */
  @Bean
  public RequestSender<Member> editMemberRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending commit change member requests.
   *
   * @return a new {@code RequestSender} instance for {@code Member}
   */
  @Bean
  public RequestSender<Member> commitChangeMemberRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending remove member requests.
   *
   * @return a new {@code RequestSender} instance for {@code Member}
   */
  @Bean
  public RequestSender<Member> removeMemberRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Creates a bean for sending add member requests.
   *
   * @return a new {@code RequestSender} instance for {@code Member}
   */
  @Bean
  public RequestSender<Member> addMemberRequestSender() {
    return new RequestSender<>();
  }


  @Bean
  RequestSender<SwitchScene> getTopFiveMembersRequestSender() {
    return new RequestSender<>();
  }

  @Bean
  RequestSender<SwitchScene> getLastestDocumentsRequestSender() {
    return new RequestSender<>();
  }

  @Bean
  RequestSender<Document> getRandomDocumentRequestSender() {
    return new RequestSender<>();
  }


  /*************************************************************************
   *  All request sender for document query: by isbn, by name, by id, by author, by category
   ***************************************************************************/

  /**
   * Send ISBN to GoogleAPI service search by ISBN
   *
   * @return the {@link RequestSender}to be registered
   */
  @Bean
  public RequestSender<String> queryISBNGoogleRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a query request by document name to service
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<String> queryDocByNameRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a query request by document author to service
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<String> queryDocByAuthorRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a query request by document category to service
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<String> queryDocByCategoryRequestSender() {
    return new RequestSender<>();
  }

  /**
   * Send a query request by document id to service
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<Integer> queryDocByIdRequestSender() {
    return new RequestSender<>();
  }

  @Bean
  public RequestSender<Integer> getDocumentByIdRequestSender() {
    return new RequestSender<>();
  }

  /*************************************************************************
   *  All request sender for member query: by id, by name
   ***************************************************************************/

  /**
   * Send a request to make member query
   *
   * @return the {@link RequestSender} to be registered
   */
  @Bean
  public RequestSender<MemberQuery> memberQueryRequestSender() {
    return new RequestSender<>();
  }

}