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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@NoArgsConstructor @Getter @Setter
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
   * Verify entity constraints.
   * <p>
   * Document's {@code name} must be a non-empty {@link String}.
   * This method strips {@code name} of its trailing empty spaces.
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
}
