package threeoone.bigproject.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;

import java.util.List;

/**
 * Simple Spring Data JPA repository for {@link Document} entity.
 *
 * @author DUCBRICK
 */
public interface DocumentRepo extends ListCrudRepository <Document, Integer> {
    List<Document> findTop5ByOrderByIdDesc();
  /**
   * Retrieves a {@link Document} with a specific {@code id} and its {@code lendingDetails}.
   * This method only executes a single SQL query.
   * This method retrieves the desired {@link Document} and details about all of its lent copies,
   * as opposed to {@link #findById}, which only retrieves the desired {@link Document} but not its {@code lendingDetails}.
   * If no {@link Document} with the specified {@code id} exists, this method returns {@code NULL}.
   *
   * @apiNote The return Document's {@code lendingDetails} list will not be {@code NULL}.
   *
   * @param id the id of the desired {@link Document}
   *
   * @return the {@link Document} with the input id, along with its {@code lendingDetails},
   *         or {@code NULL} if it doesn't exist
   *
   * @see Document
   * @see LendingDetail
   */
  @Query("SELECT d FROM Document d LEFT JOIN FETCH d.lendingDetails WHERE d.id = (:id)")
  Document findWithLendingDetails(@Param("id") int id);

  @Query("SELECT d FROM Document d WHERE d.copies > 0 ORDER BY RANDOM() LIMIT 1")
  Document findRandom();
}
