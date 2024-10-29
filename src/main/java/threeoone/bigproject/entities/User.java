package threeoone.bigproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * ORM Entity representing a user of the application.
 * This entity stores information required for user login and the {@link Document} they published.
 * A {@link User} has an identifier {@code id}.
 * A {@link User} has a unique {@code loginName} and a {@code password} used for signing in.
 * A {@link User} has a {@code displayName} that is visible to other Users.
 * A {@link User} can upload a number of {@link Document}, represented by {@code uploadedDocuments}.
 *
 * @see threeoone.bigproject.repositories.UserRepo
 * @see Document
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "appuser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "login_name")
  private String loginName;

  @Column(name = "password")
  private String password;

  @Column(name = "display_name")
  private String displayName;

  @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
  private List <Document> uploadedDocuments;

  /**
   * Constructs a new {@link User} from the given {@code loginName}, {@code password} and {@code displayName}.
   *
   * @param loginName the loginName of the new User
   * @param password the password of the new User
   * @param displayName the displayName of the new User
   */
  public User(String loginName, String password, String displayName) {
    this.loginName = loginName;
    this.password = password;
    this.displayName = displayName;
  }

  /**
   * Constructs a new {@link User}.
   * This empty constructor is required by JPA.
   */
  public User() {
  }

  /**
   * Adds a {@link Document} to the list of the User's uploaded documents.
   * JPA requires synchronization of both {@link User} and {@link Document},
   * meaning a {@link Document} must exist in its author's {@code uploadedDocuments} list.
   * This method will set the relationship on both the {@link User} side and {@link Document} side.
   *
   * @param document a Document uploaded by this User
   */
  public void addUploadedDocument(Document document) {
    if (uploadedDocuments == null) {
      uploadedDocuments = new ArrayList <> ();
    }

    uploadedDocuments.add(document);
    document.setUploader(this);
  }

  /**
   * Returns the {@code id} of the {@link User}.
   * This getter is required by JPA.
   *
   * @return the {@code id} of the {@link User}
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the {@code id} of the {@link User}.
   * This setter is required by JPA.
   *
   * @param id the new {@code id} of the {@link User}
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the {@code loginName} of the {@link User}.
   * This getter is required by JPA.
   *
   * @return the {@code loginName} of the {@link User}
   */
  public String getLoginName() {
    return loginName;
  }

  /**
   * Sets the {@code loginName} of the {@link User}.
   * This setter is required by JPA.
   *
   * @param loginName the new {@code loginName} of the {@link User}
   */
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  /**
   * Returns the {@code password} of the {@link User}.
   * This getter is required by JPA.
   *
   * @return the {@code password} of the {@link User}
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the {@code password} of the {@link User}.
   * This setter is required by JPA.
   *
   * @param password the new {@code password} of the {@link User}
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the {@code displayName} of the {@link User}.
   * This getter is required by JPA.
   *
   * @return the {@code displayName} of the {@link User}
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the {@code displayName} of the {@link User}.
   * This setter is required by JPA.
   *
   * @param displayName the new {@code displayName} of the {@link User}
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Returns the {@code uploadedDocuments} list of the {@link User}.
   * This getter is required by JPA.
   *
   * @return the {@code uploadedDocuments} list of the {@link User}
   */
  public List<Document> getUploadedDocuments() {
    return uploadedDocuments;
  }

  /**
   * Sets the {@code uploadedDocuments} list of the {@link User}.
   * This setter is required by JPA.
   *
   * @param uploadedDocuments the new {@code uploadedDocuments} list of the {@link User}
   */
  public void setUploadedDocuments(
      List<Document> uploadedDocuments) {
    this.uploadedDocuments = uploadedDocuments;
  }
}
