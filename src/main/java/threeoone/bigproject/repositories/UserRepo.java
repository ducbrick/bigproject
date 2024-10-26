package threeoone.bigproject.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import threeoone.bigproject.entities.User;

/**
 * Simple JPA repository for {@link User} entity.
 *
 * @author DUCBRICK
 */
public interface UserRepo extends ListCrudRepository <User, Integer> {
  @Query("SELECT u FROM User u LEFT JOIN FETCH u.publishedDocuments document WHERE u.id = (:id)")
  public User findUserAndPublishedDocuments(@Param("id") int id);
}
