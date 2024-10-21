package threeoone.bigproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.Document;

/**
 * Simple Spring Data JPA's repository for {@link Document} entity.
 *
 * @author DUCBRICK
 */
public interface DocumentRepo extends ListCrudRepository <Document, Integer> {
}
