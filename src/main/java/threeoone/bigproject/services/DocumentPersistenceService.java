package threeoone.bigproject.services;

import java.util.NoSuchElementException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;
import threeoone.bigproject.repositories.DocumentRepo;

/**
 * A service to modify, which is to add, update, or delete {@link Document} entities from the Database.
 * This class is a singleton bean in Spring context.
 *
 * @author DUCBRICK
 */
@Service
@RequiredArgsConstructor
public class DocumentPersistenceService {
  private final DocumentRepo documentRepo;

  @Deprecated
  public void saveNew(Document document) throws IllegalDocumentInfoException {
    if (document == null) {
      throw new IllegalArgumentException("Can not save a NULL Document");
    }

    document.setId(null);
    documentRepo.save(document);
  }

  /**
   * Saves a new {@link Document} or update an existing {@link Document}.
   * If the input {@link Document} has no {@code id} (or in other words, its {@code id} is {@code NULL}),
   * this method saves a new {@link Document} to the Database.
   * Otherwise, this method updates the pre-existing {@link Document} whose {@code id} is the same as the given Document's.
   *
   * @apiNote This method returns the saved {@link Document} Entity instance,
   * which may be different from the given instance and may have different data.
   *
   * @param document the {@link Document} to update
   *
   * @throws IllegalDocumentInfoException when the {@code uploader} of the given {@link Document} is {@code NULL}
   * @throws NoSuchElementException when the given Document's {@code id} doesn't exist in the Database.
   * @throws IllegalArgumentException when the given {@link Document} is {@code NULL}
   * @throws RuntimeException when unexpected errors occur when working with Database (such as constraints errors)
   *
   * @return the saved {@link Document} Entity instance, which may be different from the given instance
   */
  @Transactional
  public Document update(@NonNull Document document) {
    if (document.getUploader() == null) {
      throw new IllegalDocumentInfoException("Attempting to update a Document without an uploader");
    }

    if (document.getId() != null && !documentRepo.existsById(document.getId())) {
      throw new NoSuchElementException("Attempting to update a non-existent Document");
    }

    return documentRepo.save(document);
  }

  /**
   * Deletes the {@link Document} with the given {@code id}.
   * If the {@link Document} is not found in the persistence store it is silently ignored.
   *
   * @param id the {@code id} of the {@link Document} to delete
   */
  @Transactional
  public void delete(int id) {
    documentRepo.deleteById(id);
  }
}
