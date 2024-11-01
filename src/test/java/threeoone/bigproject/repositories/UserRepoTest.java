package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepoTest {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private DocumentRepo documentRepo;

  @Test
  @DisplayName("Count before and after single insertion")
  public void countBeforeAndAfterSingleInsertion() {
    long countBefore = userRepo.count();

    userRepo.save(new User("loginname", "password", "display name"));

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + 1);
  }

  @Test
  @DisplayName("Count before and after multiple insertion")
  public void countBeforeAndAfterMultipleInsertion() {
    long countBefore = userRepo.count();

    List <User> users = new ArrayList <> ();
    users.add(new User("username a", "password a", "display name a"));
    users.add(new User("username b", "password b", "display name b"));
    users.add(new User("username c", "password c", "display name c"));
    users.add(new User("username d", "password d", "display name d"));

    for (User user : users) {
      userRepo.save(user);
    }

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + users.size());
  }

  @Test
  @DisplayName("Count before and after list insertion")
  public void countBeforeAndAfterListInsertion() {
    long countBefore = userRepo.count();

    List <User> users = new ArrayList <> ();
    users.add(new User("username a", "password a", "display name a"));
    users.add(new User("username b", "password b", "display name b"));
    users.add(new User("username c", "password c", "display name c"));
    users.add(new User("username d", "password d", "display name d"));

    userRepo.saveAll(users);

    long countAfter = userRepo.count();

    assertThat(countAfter).isEqualTo(countBefore + users.size());
  }

  @Test
  @DisplayName("Insert documents and retrieve uploaders")
  public void insertDocuments_retrieveAuthor() {
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

    user = docA.getUploader();

    assertThat(user).isSameAs(docA.getUploader());
    assertThat(user).isSameAs(docB.getUploader());
    assertThat(user).isSameAs(docC.getUploader());

    user = userRepo.findUserAndUploadedDocuments(user.getId());
    List <Document> documents = user.getUploadedDocuments();

    assertThat(documents).isNotNull();
    assertThat(documents.size()).isEqualTo(3);

    assertThat(documents.contains(docA)).isTrue();
    assertThat(documents.contains(docB)).isTrue();
    assertThat(documents.contains(docC)).isTrue();
  }

  @Test
  @DisplayName("Insert user and retrieve document")
  public void insertUserRetrieveDocument() {
    User user = new User("name", "password", "Fancy Name");
    user.addUploadedDocument(new Document("name", "desc"));

    user = userRepo.save(user);

    List <Document> documents = user.getUploadedDocuments();

    assertThat(documents).isNotNull();

    assertThat(documents.size()).isEqualTo(1);

    Document document = documents.getFirst();

    assertThat(document).isSameAs(documentRepo.findById(document.getId()).orElse(null));
  }

  @Test
  @DisplayName("Find user by login name")
  public void findByLoginName() {
    User user = new User("ducbrick", "password", "Brick");
    user = userRepo.save(user);
    assertThat(user).isSameAs(userRepo.findByLoginName(user.getLoginName()));
  }

  @Test
  @DisplayName("Find non-existent user by login name")
  public void findNonExistentUserByLoginName() {
    String random = "jshdflkasdjfsadf";
    assertThat(userRepo.findByLoginName(random)).isNull();
  }

  @Test
  @DisplayName("Find non-existent user and documents by id")
  public void findNonExistentUserById_findUserAndUploadedDocuments() {
    int randomId = new Random().nextInt(-100, 100);
    assertThat(userRepo.findUserAndUploadedDocuments(randomId)).isNull();
  }
}