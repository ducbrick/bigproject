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
  @DisplayName("Delete a Document")
  public void delete() {
    int id = 1;

    documentPersistenceService.delete(id);

    verify(documentRepo, times(1)).deleteById(id);
  }
}