package threeoone.bigproject.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import threeoone.bigproject.entities.Member;

/**
 * JPA repository for {@link Member} entity.
 *
 * @author DUCBRICK
 */
public interface MemberRepo extends ListCrudRepository <Member, Integer> {
  /**
   * Retrieves a List of Members whose name contain the given substring.
   *
   * @apiNote the list returned will not be {@code NULL}.
   * @apiNote this method is not case-sentitive.
   *
   * @param substr the substring to retrieve Members
   *
   * @return the List of Members whose name contain the given substring
   */
  @Query("SELECT m from Member m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :substr, '%'))")
  List <Member> findWithNameContaining(@Param("substr") String substr);
}
