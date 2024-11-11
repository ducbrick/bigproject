package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;

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
    Document document = new Document("name", "description", 1);
    assertThatThrownBy(() -> documentRepo.save(document));
  }

  @Test
  @DisplayName("Insert into database")
  public void insertIntoDatabase() {
    User user = new User("name", "password");
    Document document = new Document("name", "description", 1);
    user.addUploadedDocument(document);

    long countBefore = documentRepo.count();

    documentRepo.save(document);

    assertThat(documentRepo.count()).isEqualTo(countBefore + 1);

    Document anotherDoc = new Document("another name", "same description", 1);
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
    User user = new User("name", "password");
    Document docA = new Document("name a", "description a", 1);
    Document docB = new Document("name b", "description b", 1);
    Document docC = new Document("name c", "description c", 1);

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
    User userA = new User("name a", "password");
    User userB = new User("name b", "password");

    Document docA = new Document("name a", "description a", 1);
    Document docB = new Document("name b", "description b", 1);

    userA.addUploadedDocument(docA);
    userB.addUploadedDocument(docB);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);

    assertThat(docA.getUploader()).isNotSameAs(docB.getUploader());
  }

  @Test
  @DisplayName("Cascade insert document and uploader")
  public void cascadeInsert() {
    User user = new User("name", "password");
    Document document = new Document("name", "desc", 1);
    user.addUploadedDocument(document);

    long countBefore = userRepo.count();

    documentRepo.save(document);

    assertThat(userRepo.count()).isEqualTo(countBefore + 1);

    assertThat(document.getUploader()).isSameAs(userRepo.findById(document.getUploader().getId()).orElse(null));
  }

  @Test
  @DisplayName("Retrieve a Document with no lending copies")
  public void documentWithNoLending() {
    User user = new User("ahihi", "password");

    Document document = new Document("name");
    user.addUploadedDocument(document);

    document = documentRepo.save(document);

    document = documentRepo.findWithLendingDetails(document.getId());

    assertThat(document.getLendingDetails()).isNotNull();
    assertThat(document.getLendingDetails().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("Retrieve a Document and its lending copies")
  public void documentWithLending() {
    Member memA = new Member("name A");
    Member memB = new Member("name B");

    User user = new User("name", "password");

    Document document = new Document("name");
    user.addUploadedDocument(document);

    LendingDetail detailA = new LendingDetail(LocalDateTime.now());
    LendingDetail detailB = new LendingDetail(LocalDateTime.now());

    memA.lendDocument(detailA);
    memB.lendDocument(detailB);

    document.lendDocument(detailA);
    document.lendDocument(detailB);

    document = documentRepo.save(document);

    document = documentRepo.findWithLendingDetails(document.getId());

    List<LendingDetail> lendingDetails = document.getLendingDetails();

    assertThat(lendingDetails).isNotNull();
    assertThat(lendingDetails.size()).isEqualTo(2);

    assertThat(lendingDetails.contains(detailA)).isTrue();
    assertThat(lendingDetails.contains(detailB)).isTrue();
  }
}