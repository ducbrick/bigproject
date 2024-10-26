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
 * A {@link User} has an identifier {@code id},
 * a {@code name} and {@code password} field for login,
 * and a less restricted {@code displayName}.
 *
 * @see threeoone.bigproject.repositories.UserRepo
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

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List <Document> publishedDocuments;

  public User(String loginName, String password, String displayName) {
    this.loginName = loginName;
    this.password = password;
    this.displayName = displayName;
  }

  public User() {
  }

  public void addPublishedDocument(Document document) {
    if (publishedDocuments == null) {
      publishedDocuments = new ArrayList <> ();
    }

    publishedDocuments.add(document);
    document.setAuthor(this);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<Document> getPublishedDocuments() {
    return publishedDocuments;
  }

  public void setPublishedDocuments(
      List<Document> publishedDocuments) {
    this.publishedDocuments = publishedDocuments;
  }
}
