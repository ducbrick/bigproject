package threeoone.bigproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.User;

/**
 * Simple JPA repository for {@link User} entity.
 *
 * @author DUCBRICK
 */
public interface UserRepo extends ListCrudRepository <User, Integer> {

}
