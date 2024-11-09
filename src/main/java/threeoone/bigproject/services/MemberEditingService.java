package threeoone.bigproject.services;

import java.util.NoSuchElementException;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.repositories.MemberRepo;

/**
 * A service to modify, which is to add, update, or delete {@link Member} entities from the Database.
 * This class is a singleton bean in Spring context.
 *
 * @author DUCBRICK
 */
@Service
public class MemberEditingService {
  private final MemberRepo memberRepo;

  /**
   * Autowired constructors that obtain the necessary dependencies.
   *
   * @param memberRepo a repository for {@link Member}
   */
  public MemberEditingService(MemberRepo memberRepo) {
    this.memberRepo = memberRepo;
  }

  /**
   * Saves a new {@link Member} or update an existing {@link Member}.
   * If the input {@link Member} has no {@code id} (or in other words, its {@code id} is {@code NULL}),
   * this method saves a new {@link Member} to the Database.
   * Otherwise, this method updates the pre-existing {@link Member} whose {@code id} is the same as the given Member's.
   *
   * @param member the {@link Member} to update
   *
   * @throws IllegalArgumentException when the given {@link Member} is {@code NULL}
   * @throws NoSuchElementException when the given Member's {@code id} doesn't exist in the Database.
   * @throws RuntimeException when unexpected errors occur when working with Database (such as constraints errors)
   */
  public Member update(@NonNull Member member) {
    if (member.getId() != null && !memberRepo.existsById(member.getId())) {
      throw new NoSuchElementException("Attempting to update a non-existent Member");
    }
    return memberRepo.save(member);
  }

  /**
   * Deletes the {@link Member} with the given {@code id}.
   * If the {@link Member} is not found in the persistence store it is silently ignored.
   *
   * @param id the {@code id} of the {@link Member} to delete
   */
  public void delete(int id) {
    memberRepo.deleteById(id);
  }
}
