package threeoone.bigproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * ORM Entity representing a {@link Document} in the library database.
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "Document")
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "document_id")
  private int id;

  @Column(name = "document_name")
  private String name;

  @Column(name = "document_description")
  private String description;

  public Document(String name, String description) {
    this.name = name;
    this.description = description;
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
}
