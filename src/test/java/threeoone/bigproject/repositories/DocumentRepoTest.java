package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;
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

  @Test
  @DisplayName("Count rows in empty database")
  public void countRowsInEmptyDatabase() {
    long count = documentRepo.count();
    assertThat(count).isEqualTo(0);
  }

  @Test
  @DisplayName("Insert one row into empty database then count rows")
  public void insertOneRowIntoEmptyDatabase_thenCountRows() {
    Document document = new Document("name", "desc");
    documentRepo.save(document);
    long count = documentRepo.count();
    assertThat(count).isEqualTo(1);
  }

  @Test
  @DisplayName("Insert into database then check existence")
  public void insertIntoDb_thenCheckExistence() {
    Document document = new Document("name", "desc");
    document = documentRepo.save(document);
    int id = document.getId();
    assertThat(documentRepo.existsById(id)).isTrue();
  }


  @Test
  @DisplayName("Insert into database then retrieve")
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