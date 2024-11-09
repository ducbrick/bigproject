package threeoone.bigproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;

/**
 * ORM Entity representing a {@link Document} in the library database.
 * <p>
 * A {@link Document} has a {@link #name}, a {@link #description} and has a total of {@link #copies} physical copies.
 * A {@link Document} has an {@link #uploader},
 * representing the {@link User} who uploaded this {@link Document}.
 * <p>
 * {@link #uploader} is a bidirectional many-to-one relationship between {@link User} and {@link Document}.
 * JPA requires synchronization on both sides in order to persist.
 * {@link User#addUploadedDocument(Document)} sets the relationship on both sides.
 * Lombok-generated {@link #setUploader(User)} only sets the relationship on the {@link Document} side.
 * <p>
 * Likewise, {@link #lendingDetails} is a bidirectional one-to-many relationship between {@link Document} and {@link LendingDetail}.
 *
 * @see User
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "Document")
@NoArgsConstructor @Getter @Setter @RequiredArgsConstructor
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  private String name;

  private String description;

  @NonNull
  private Integer copies = 0;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "uploader_id")
  private User uploader;

  @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
  private List <LendingDetail> lendingDetails = new ArrayList <> ();

  /**
   * Constructs a new {@link Document} with the given parameters.
   *
   * @param name name of the new {@link Document}
   * @param description description of the new {@link Document}
   * @param copies total number of copies of this Document
   */
  public Document(@NonNull String name, String description, @NonNull Integer copies) {
    this.name = name;
    this.description = description;
    this.copies = copies;
  }

  /**
   * Constructs a new {@link Document} with the given parameters.
   *
   * @param name name of the new {@link Document}
   * @param description description of the new {@link Document}
   */
  public Document(@NonNull String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Verify entity constraints.
   * <p>
   * Document's {@link #name} must be a non-empty {@link String}.
   * This method strips {@link #name} of its trailing empty spaces.
   * <p>
   * Document's {@link #uploader} must not be {@code NULL}.
   *
   * @throws IllegalDocumentInfoException when the Document's attributes don't adhere to the constraints
   */
  public void checkConstraints() throws IllegalDocumentInfoException {
    name = name.stripTrailing();
    if (name.isEmpty()) {
      throw new IllegalDocumentInfoException("Document name is empty");
    }

    if (uploader == null) {
      throw new IllegalDocumentInfoException("Document have no uploader");
    }
  }

  /**
   * Adds a {@link LendingDetail} to the list of the Document's lent copies.
   * JPA requires synchronization on both sides in order to persist,
   * meaning a {@link LendingDetail} must exist in its Document's {@link #lendingDetails} list.
   * This method sets the relationship on both the {@link Document} side and {@link LendingDetail} side.
   *
   * @param lendingDetail detail about the lent copy
   */
  public void lendDocument(LendingDetail lendingDetail) {
    lendingDetails.add(lendingDetail);
    lendingDetail.setDocument(this);
  }
}
