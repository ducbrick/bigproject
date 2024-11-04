package threeoone.bigproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;

/**
 * ORM Entity representing a {@link Document} in the library database.
 * A {@link Document} has an identifier {@code id}, a {@code name} and a {@code description}.
 * A {@link Document} has an {@code uploader},
 * representing the {@link User} who uploaded this {@link Document}.
 *
 * @see threeoone.bigproject.repositories.DocumentRepo
 * @see User
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "Document")
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "uploader_id")
  private User uploader;

  /**
   * Constructs a new {@link Document} with the given {@code name} and {@code description}.
   *
   * @param name name of the new {@link Document}
   * @param description description of the new {@link Document}
   */
  public Document(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Constructs a new {@link Document}.
   * This empty constructor is required by JPA.
   */
  public Document() {
  }

  /**
   * Verify entity constraints.
   * <p>
   * Document's {@code name} must be a non-empty {@link String}.
   * <p>
   * Document's {@code uploader} must not be {@code NULL}.
   *
   * @throws IllegalDocumentInfoException when the Document's attributes don't adhere to the constraints
   */
  public void checkConstraints() throws IllegalDocumentInfoException {
    if (name == null) {
      throw new IllegalDocumentInfoException("Document name is NULL");
    }
    name = name.stripTrailing();
    if (name.isEmpty()) {
      throw new IllegalDocumentInfoException("Document name is empty");
    }

    if (uploader == null) {
      throw new IllegalDocumentInfoException("Document have no uploader");
    }
  }

  /**
   * Returns the {@code id} of the {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code id} of the {@link Document}
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the {@code id} of the {@link Document}.
   * This setter is required by JPA.
   *
   * @param id the new {@code id} of the {@link Document}
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Returns the {@code name} of the {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code name} of the {@link Document}
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the {@code name} of the {@link Document}.
   * This setter is required by JPA.
   *
   * @param name the new {@code name} of the {@link Document}
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the {@code description} of the {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code description} of the {@link Document}
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the {@code description} of the {@link Document}.
   * This setter is required by JPA.
   *
   * @param description the new {@code description} of the {@link Document}
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the {@code uploader} of the {@link Document},
   * which is the {@link User} who uploaded this {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code uploader} of the {@link Document}
   */
  public User getUploader() {
    return uploader;
  }

  /**
   * Sets the {@code uploader} of the {@link Document},
   * which is the {@link User} who uploaded this {@link Document}.
   * This setter is required by JPA.
   * <p>
   * JPA requires synchronization of both {@link User} and {@link Document},
   * meaning a {@link Document} must exist in its uploader's {@code uploadedDocuments} list.
   * This method will only set the relationship on the {@link Document} side,
   * but not on the {@link User} side.
   * To set the relationship on both side, use {@link User#addUploadedDocument(Document)}.
   *
   * @param uploader the new {@code uploader} of the {@link Document}
   */
  public void setUploader(User uploader) {
    this.uploader = uploader;
  }
}
