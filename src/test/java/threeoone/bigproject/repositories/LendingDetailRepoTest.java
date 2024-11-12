package threeoone.bigproject.repositories;

import java.time.LocalDateTime;
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
class LendingDetailRepoTest {
  @Autowired
  private LendingDetailRepo lendingDetailRepo;

  @Test
  @DisplayName("Test build & run")
  public void test() {
    LendingDetail lendingDetail = new LendingDetail(LocalDateTime.now());
    Member member = new Member("name");
    Document document = new Document("name");
    User user = new User("name", "password");

    member.lendDocument(lendingDetail);
    document.lendDocument(lendingDetail);
    user.addUploadedDocument(document);

    lendingDetail = lendingDetailRepo.save(lendingDetail);


  }
}