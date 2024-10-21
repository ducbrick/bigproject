package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import threeoone.bigproject.entities.Document;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DocumentRepoTest {
  @Autowired
  private DocumentRepo documentRepo;

  @Test
  @DisplayName("Insert into database then verify by retrieving")
  public void insert_thenRetrieve() {
    String name = "name";
    String desc = "desc";
    Document document = new Document(name, desc);

    int id = documentRepo.save(document).getId();

    Optional <Document> queryRes = documentRepo.findById(id);

    assertThat(queryRes.isPresent()).isTrue();

    Document queryDocument = queryRes.get();

    assertThat(name).isEqualTo(queryDocument.getName());
    assertThat(desc).isEqualTo(queryDocument.getDescription());
  }
}