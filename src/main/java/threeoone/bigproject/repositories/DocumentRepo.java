package threeoone.bigproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.Document;

public interface DocumentRepo extends ListCrudRepository <Document, Integer> {
}
