package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
  @DisplayName("Insert a document without uploader")
  public void insertDocumentWithoutAuthor() {
    Document document = new Document("name", "description");
    assertThatThrownBy(() -> documentRepo.save(document));
  }

  @Test
  @DisplayName("Insert into database")
  public void insertIntoDatabase() {
    User user = new User("name", "password", "Fancy Name");
    Document document = new Document("name", "description");
    user.addUploadedDocument(document);

    long countBefore = documentRepo.count();

    documentRepo.save(document);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 1);

    Document anotherDoc = new Document("another name", "same description");
    user.addUploadedDocument(anotherDoc);

    documentRepo.save(anotherDoc);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 2);

    document.setName("old name");

    documentRepo.save(document);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 2);
  }

  @Test
  @DisplayName("Insert documents with the same uploader")
  public void insertDocumentsWithSameAuthor() {
    User user = new User("name", "password", "Fancy Name");
    Document docA = new Document("name a", "description a");
    Document docB = new Document("name b", "description b");
    Document docC = new Document("name c", "description c");

    user.addUploadedDocument(docA);
    user.addUploadedDocument(docB);
    user.addUploadedDocument(docC);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);
    docC = documentRepo.save(docC);

    User sameAuthor = docA.getUploader();

    assertThat(docB.getUploader()).isSameAs(sameAuthor);
    assertThat(docC.getUploader()).isSameAs(sameAuthor);
  }

  @Test
  @DisplayName("Insert documents with different uploaders")
  public void insertDocumentsWithDifferentAuthors() {
    User userA = new User("name a", "password", "Fancy Name");
    User userB = new User("name b", "password", "Fancy Name");

    Document docA = new Document("name a", "description a");
    Document docB = new Document("name b", "description b");

    userA.addUploadedDocument(docA);
    userB.addUploadedDocument(docB);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);

    assertThat(docA.getUploader()).isNotSameAs(docB.getUploader());
  }

  @Test
  @DisplayName("Cascade insert document and uploaders")
  public void cascadeInsert() {
    User user = new User("name", "password", "Fancy Name");
    Document document = new Document("name", "desc");
    user.addUploadedDocument(document);

    long countBefore = userRepo.count();

    documentRepo.save(document);

    assertThat(userRepo.count()).isEqualTo(countBefore + 1);

    assertThat(document.getUploader()).isSameAs(userRepo.findById(document.getUploader().getId()).orElse(null));
  }
}