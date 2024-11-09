package threeoone.bigproject.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;
import threeoone.bigproject.repositories.DocumentRepo;

@ExtendWith(MockitoExtension.class)
class DocumentPersistenceServiceTest {
  @Mock
  private DocumentRepo documentRepo;

  @InjectMocks
  private DocumentPersistenceService documentPersistenceService;

  @Test
  @DisplayName("Update a Document with no uploader")
  public void updateDocumentNoUploader() {
    Document document = new Document("name");
    assertThatThrownBy(() -> documentPersistenceService.update(document))
        .isInstanceOf(IllegalDocumentInfoException.class);
  }

  @Test
  @DisplayName("Add a new Document")
  public void addNew() throws IllegalDocumentInfoException {
    User user = new User("name", "password");
    Document document = new Document("name");
    document.setUploader(user);

    documentPersistenceService.update(document);
    verify(documentRepo, times(1)).save(document);
  }

  @Test
  @DisplayName("Update a non-existent Document")
  public void updateNonExistentDocument() {
    User user = new User("name", "password");
    Document document = new Document("name");
    document.setUploader(user);
    document.setId(1);

    when(documentRepo.existsById(document.getId())).thenReturn(false);

    assertThatThrownBy(() -> documentPersistenceService.update(document))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  @DisplayName("Update an existing Document")
  public void updateExistingDocument() throws IllegalDocumentInfoException {
    User user = new User("name", "password");
    Document document = new Document("name");
    document.setUploader(user);
    document.setId(1);

    when(documentRepo.existsById(document.getId())).thenReturn(true);

    documentPersistenceService.update(document);

    verify(documentRepo, times(1)).save(document);
  }

  @Test
  @DisplayName("Delete a Document")
  public void delete() {
    int id = 1;

    documentPersistenceService.delete(id);

    verify(documentRepo, times(1)).deleteById(id);
  }
}