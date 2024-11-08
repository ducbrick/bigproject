package threeoone.bigproject.entities;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;

class DocumentTest {
  @Test
  @DisplayName("Check constraints for NULL name")
  public void constraintsNullName() {
    User user = new User("username", "password");
    Document document = new Document(null, null);
    user.addUploadedDocument(document);
    assertThatThrownBy(document::checkConstraints).isInstanceOf(IllegalDocumentInfoException.class);
  }

  @Test
  @DisplayName("Check constraints for empty name")
  public void constraintsEmptyName() {
    User user = new User("username", "password");
    Document document = new Document("  ", null);
    user.addUploadedDocument(document);
    assertThatThrownBy(document::checkConstraints).isInstanceOf(IllegalDocumentInfoException.class);
  }

  @Test
  @DisplayName("Check constraints for NULL uploader")
  public void constraintsNullUploader() {
    User user = new User("username", "password");
    Document document = new Document("name", null);
    assertThatThrownBy(document::checkConstraints).isInstanceOf(IllegalDocumentInfoException.class);
  }
}