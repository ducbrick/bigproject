package threeoone.bigproject.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.User;

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
   * @apiNote this method is not case-sensitive.
   *
   * @param substr the substring to retrieve Members
   *
   * @return the List of Members whose name contain the given substring
   */
  @Query("SELECT m from Member m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :substr, '%'))")
  List <Member> findWithNameContaining(@Param("substr") String substr);

  /**
   * Retrieves a {@link Member} with a specific {@code id} and its {@code lendingDetails}.
   * This method only executes a single SQL query.
   * This method retrieves the desired {@link Member} and details all of its lending {@link Document},
   * as opposed to {@link #findById}, which only retrieves the desired {@link Member} but not its {@code lendingDetails}.
   *
   * @param id the id of the desired {@link Member}
   *
   * @return the {@link Member} with the input id, along with its {@code lendingDetails}.
   *
   * @see Member
   * @see LendingDetail
   */
  @Query("SELECT m FROM Member m LEFT JOIN FETCH m.lendingDetails WHERE m.id = (:id)")
  Member findWithLendingDetails(@Param("id") int id);
}
