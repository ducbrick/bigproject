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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@NoArgsConstructor @Getter @Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
  private List <Document> uploadedDocuments;

  /**
   * Constructs a new {@link User} from the given {@code loginName} and {@code password}.
   *
   * @param username the loginName of the new User
   * @param password the password of the new User
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
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
}
