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
 * A {@link Document} has a primary key {@code id}, {@code name} and {@code description}.
 *
 * @see threeoone.bigproject.repositories.DocumentRepo
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

  public Document(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Document(String name, String description, User author) {
    this.name = name;
    this.description = description;
    this.author = author;
  }

  public Document() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }
}
