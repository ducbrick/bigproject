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

/**
 * ORM Entity representing a {@link Document} in the library database.
 * A {@link Document} has an identifier {@code id}, a {@code name} and a {@code description}.
 * A {@link Document} has an {@code author},
 * representing the {@link User} who published this {@link Document}.
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
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "author_id")
  private User author;

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
   * Returns the {@code id} of the {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code id} of the {@link Document}
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the {@code id} of the {@link Document}.
   * This setter is required by JPA.
   *
   * @param id the new {@code id} of the {@link Document}
   */
  public void setId(int id) {
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
   * Returns the {@code author} of the {@link Document},
   * which is the {@link User} who published this {@link Document}.
   * This getter is required by JPA.
   *
   * @return the {@code author} of the {@link Document}
   */
  public User getAuthor() {
    return author;
  }

  /**
   * Sets the {@code author} of the {@link Document},
   * which is the {@link User} who published this {@link Document}.
   * This setter is required by JPA.
   *
   * @param author the new {@code author} of the {@link Document}
   */
  public void setAuthor(User author) {
    this.author = author;
  }
}
