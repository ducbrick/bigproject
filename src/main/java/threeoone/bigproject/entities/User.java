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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * ORM Entity representing a user of the application.
 * <p>
 * This entity stores information required for user login and the documents they have uploaded.
 * A {@link User} has a unique {@link #username} and a {@link #password} used authentication.
 * A {@link User} can upload a number of {@link Document}, represented by {@link #uploadedDocuments}.
 * <p>
 * {@link #uploadedDocuments} is a bidirectional one-to-many relationship between {@link User} and {@link Document}.
 * JPA requires synchronization on both sides in order to persist.
 * {@link #addUploadedDocument(Document)} sets the relationship on both sides.
 * Lombok-generated {@link Document#setUploader(User)} only sets the relationship on the {@link Document} side.
 *
 * @see Document
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "app_user")
@NoArgsConstructor @Getter @Setter @RequiredArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  private String username;

  @NonNull
  private String password;

  @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL, orphanRemoval = true)
  private List <Document> uploadedDocuments = new ArrayList <> ();

  /**
   * Adds a {@link Document} to the list of the User's uploaded documents.
   * JPA requires synchronization on both sides in order to persist,
   * meaning a {@link Document} must exist in its author's {@link #uploadedDocuments} list.
   * This method sets the relationship on both the {@link User} side and {@link Document} side.
   *
   * @param document a Document uploaded by this User
   */
  public void addUploadedDocument(Document document) {
    uploadedDocuments.add(document);
    document.setUploader(this);
  }
}
