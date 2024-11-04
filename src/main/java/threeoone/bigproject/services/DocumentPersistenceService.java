package threeoone.bigproject.services;

import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;
import threeoone.bigproject.repositories.DocumentRepo;

/**
 * A service to persists {@link Document} entities to the Database.
 * This class is a singleton bean in Spring context.
 *
 * @author DUCBRICK
 */
@Service
public class DocumentPersistenceService {
  private final DocumentRepo documentRepo;

  /**
   * Autowired constructor to obtain the necessary dependencies
   *
   * @param documentRepo a {@link Document} repository
   */
  public DocumentPersistenceService(DocumentRepo documentRepo) {
    this.documentRepo = documentRepo;
  }

  /**
   * Save a new {@link Document} to the Database.
   * This method verifies Document's constraints by calling {@link Document#checkConstraints()} before saving.
   * This method sets the Document's {@code id} to {@code NULL} and JPA will assign a new {@code id} to it.
   *
   * @param document the {@link Document} to save.
   *
   * @throws IllegalArgumentException when document is {@code NULL}
   * @throws IllegalDocumentInfoException when document doesn't follow {@link Document} constraints
   * @throws RuntimeException when fails to work with the Database
   */
  public void saveNew(Document document) throws IllegalDocumentInfoException {
    if (document == null) {
      throw new IllegalArgumentException("Can not save a NULL Document");
    }

    document.checkConstraints();

    document.setId(null);
    documentRepo.save(document);
  }
}
