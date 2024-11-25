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
import java.time.LocalDateTime;
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
 * A {@link Document} has a {@link #name},
 *                        a {@link #description},
 *                        a total of {@link #copies} physical copies,
 *                        an {@link #author},
 *                        an {@link #isbn},
 *                        a cover image at {@link #coverImageUrl}
 *                        and uploaded at {@link #uploadTime}.
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
@Table(name = "document")
@NoArgsConstructor @Getter @Setter
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  private String name;

  private String description;

  @NonNull
  private Integer copies = 0;

  private String author;

  private String isbn;

  @Column(name = "upload_time")
  private LocalDateTime uploadTime;

  private String category;

  @NonNull
  @Column(name = "cover_image_url")
  private String coverImageUrl = "threeoone/bigproject/controller/viewcontrollers/No_image_available.png";

  @Column(name = "pdf_url")
  private String pdfUrl;

  @Column(name = "info_url")
  private String infoUrl;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "uploader_id", nullable = false)
  private User uploader;

  @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
  private List <LendingDetail> lendingDetails = new ArrayList <> ();

  /**
   * Constructs a new {@link Document} with the given parameters.
   *
   * @param name the name of the new {@link Document}
   */
  public Document(@NonNull String name) {
    this.name = name;
  }

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
