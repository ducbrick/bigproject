package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
  @DisplayName("Insert documents and retrieve author")
  public void insertDocuments_retrieveAuthor() {
    User user = new User("name", "password", "Fancy Name");
    Document docA = new Document("name a", "description a");
    Document docB = new Document("name b", "description b");
    Document docC = new Document("name c", "description c");

    user.addPublishedDocument(docA);
    user.addPublishedDocument(docB);
    user.addPublishedDocument(docC);

    docA = documentRepo.save(docA);
    docB = documentRepo.save(docB);
    docC = documentRepo.save(docC);

    user = docA.getAuthor();

    assertThat(user).isSameAs(docA.getAuthor());
    assertThat(user).isSameAs(docB.getAuthor());
    assertThat(user).isSameAs(docC.getAuthor());

    user = userRepo.findUserAndPublishedDocuments(user.getId());
    List <Document> documents = user.getPublishedDocuments();

    assertThat(documents).isNotNull();
    assertThat(documents.size()).isEqualTo(3);

    assertThat(documents.contains(docA)).isTrue();
    assertThat(documents.contains(docB)).isTrue();
    assertThat(documents.contains(docC)).isTrue();
  }

  @Test
  @DisplayName("test")
  public void test() {
    User user = userRepo.findUserAndPublishedDocuments(67);

    List <Document> documents = user.getPublishedDocuments();

    assertThat(documents).isNotNull();

    for (Document document : documents) {
      System.out.println(document.getId());
      System.out.println(document.getName());
      System.out.println(document.getDescription());
      System.out.println();
    }
  }
}