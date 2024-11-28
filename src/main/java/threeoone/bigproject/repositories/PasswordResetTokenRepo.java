package threeoone.bigproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.PasswordResetToken;

public interface PasswordResetTokenRepo extends ListCrudRepository <PasswordResetToken, Integer> {

}
