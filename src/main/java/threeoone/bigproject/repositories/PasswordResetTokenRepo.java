package threeoone.bigproject.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.PasswordResetToken;
import threeoone.bigproject.entities.User;

/**
 * Jpa repository for {@link PasswordResetToken} entity.
 *
 * @author DUCBRICK
 */
public interface PasswordResetTokenRepo extends ListCrudRepository <PasswordResetToken, Integer> {
  /**
   * Delete any, every and all {@link PasswordResetToken} associated to a specific {@link User}.
   *
   * @param userId the {@code id} of the {@link User} whose tokens are to be deleted
   */
  @Modifying
  @Query("DELETE FROM PasswordResetToken t WHERE t.user.id = :userId")
  void deleteByUser(Integer userId);
}
