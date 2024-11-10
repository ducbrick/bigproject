package threeoone.bigproject.services;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.repositories.MemberRepo;

/**
 * A Service that supports basic operations that retrieve Members from the database.
 * This class is a singleton bean in Spring Context.
 *
 * @see Member
 *
 * @author DUCBRICK
 */
@Service
@RequiredArgsConstructor
public class MemberRetrievalService {
  private final MemberRepo memberRepo;

  /**
   * Retrieves all {@link Member} from the Database as a List.
   *
   * @apiNote the returned List will not be {@code NULL}.
   *
   * @return a {@link List} containing every {@link Member} in the Database
   */
  public List <Member> getAll() {
    return memberRepo.findAll();
  }

  /**
   * Retrieves a {@link Member} with a specific {@code id}.
   * If no {@link Member} with the input {@code id} exists in the Database,
   * this method returns {@code NULL}.
   *
   * @param id the {@code id} of the {@link Member} to be retrieved
   *
   * @return the desired {@link Member} if it exists in the Database, or {@code NULL} if otherwise
   */
  public Member findById(int id) {
    Optional <Member> queryResult = memberRepo.findById(id);
    return queryResult.orElse(null);
  }

  /**
   * Retrieves all Members whose name s contain a specific String as a List.
   *
   * @apiNote The returned List will not be {@code NULL}
   *
   * @param string the String to query
   *
   * @return the List of Members whose names contain the given String
   */
  public List <Member> findWhoseNamesContain(@NonNull String string) {
    return memberRepo.findWithNameContaining(string);
  }
}
