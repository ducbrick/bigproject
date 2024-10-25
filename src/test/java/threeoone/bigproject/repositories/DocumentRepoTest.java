package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.bouncycastle.pqc.crypto.newhope.NHSecretKeyProcessor.PartyUBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;

/**
 * All tests assume the database is currently empty.
 *
 * @author DUCBRICK
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DocumentRepoTest {
  @Autowired
  private DocumentRepo documentRepo;

  @Autowired
  private UserRepo userRepo;

  @Test
  @DisplayName("Test compile & runtime")
  public void testCompile() {
    documentRepo.count();
  }

  @Test
  @DisplayName("Insert a document without author")
  public void insertDocumentWithoutAuthor() {
    Document document = new Document("name", "description");
    assertThatThrownBy(() -> documentRepo.save(document));
  }

  @Test
  @DisplayName("Insert into database")
  public void insertIntoDatabase() {
    User user = new User("name", "password", "Fancy Name");
    Document document = new Document("name", "description", user);

    long countBefore = documentRepo.count();

    documentRepo.save(document);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 1);

    Document anotherDoc = new Document("another name", "same description", user);

    documentRepo.save(anotherDoc);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 2);

    document.setName("old name");

    documentRepo.save(document);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 2);
  }

  @Test
  @DisplayName("Insert documents with the same author")
  public void insertDocumentsWithSameAuthor() {
    User user = new User("name", "password", "Fancy Name");
    Document docA = new Document("name a", "description a", user);
    Document docB = new Document("name b", "description b", user);
    Document docC = new Document("name c", "description c", user);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);
    docC = documentRepo.save(docC);

    User sameAuthor = docA.getAuthor();

    assertThat(docB.getAuthor()).isSameAs(sameAuthor);
    assertThat(docC.getAuthor()).isSameAs(sameAuthor);
  }

  @Test
  @DisplayName("Insert documents with different authors")
  public void insertDocumentsWithDifferentAuthors() {
    User userA = new User("name", "password", "Fancy Name");
    User userB = new User("name", "password", "Fancy Name");
    Document docA = new Document("name a", "description a", userA);
    Document docB = new Document("name b", "description b", userB);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);

    assertThat(docA.getAuthor()).isNotSameAs(docB.getAuthor());
  }

  @Test
  @DisplayName("Cascade insert document and author")
  public void cascadeInsert() {
    User user = new User("name", "password", "Fancy Name");
    Document document = new Document("name", "desc", user);

    long countBefore = userRepo.count();

    documentRepo.save(document);

    assertThat(userRepo.count()).isEqualTo(countBefore + 1);

    assertThat(document.getAuthor()).isSameAs(userRepo.findById(document.getAuthor().getId()).orElse(null));
  }
}