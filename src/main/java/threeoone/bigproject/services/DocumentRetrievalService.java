package threeoone.bigproject.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.repositories.DocumentRepo;

/**
 * A {@code Service} that supports basic operations that retrieve {@link Document} from the database.
 * This class is a singleton bean in Spring Context.
 *
 * @see Document
 *
 * @author DUCBRICK
 */
@Service
@RequiredArgsConstructor
public class DocumentRetrievalService {
  private final DocumentRepo documentRepo;

  /**
   * Retrieves all {@link Document} in the Database.
   *
   * @return a {@link List} containing every {@link Document} in the Database
   */
  public List <Document> getAllDocuments() {
    return documentRepo.findAll();
  }

  /**
   * Gets a {@link Document} with a specific {@code id}.
   * If no {@link Document} with the input {@code id} exists in the Database,
   * this method returns {@code null}.
   *
   * @param id the {@code id} of the {@link Document} to retrieve
   *
   * @return the desired {@link Document} if it exists in the Database, otherwise, {@code null}
   */
  public Document getDocumentById(int id) {
    Optional <Document> queryResult = documentRepo.findById(id);
    return queryResult.orElse(null);
  }

  /**
   * gets a random document which has copies > 0 (borrow-able)
   * if no document exists in database:
   * @return a random Document
   * TODO: handles when no document available
   */
  public Document getRandomDocument(){
    return documentRepo.findRandom();
  }

  /**
   * gets the last 5 document (sort by ID)
   * if less than 5 document exist, it will return that number of documents
   * @return a List of Document
   */
  public List<Document> getLatestDocuments() {
    return documentRepo.findTop5ByOrderByIdDesc();
  }
}