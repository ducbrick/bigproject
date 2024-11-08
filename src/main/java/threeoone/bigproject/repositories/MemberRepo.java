package threeoone.bigproject.repositories;

import org.springframework.data.repository.ListCrudRepository;
import threeoone.bigproject.entities.Member;

public interface MemberRepo extends ListCrudRepository <Member, Integer> {
}
