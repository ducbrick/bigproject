package threeoone.bigproject.services;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import javax.print.Doc;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threeoone.bigproject.entities.Document;
import threeoone.bigproject.entities.LendingDetail;
import threeoone.bigproject.entities.Member;
import threeoone.bigproject.exceptions.IllegalDocumentInfoException;
import threeoone.bigproject.repositories.LendingDetailRepo;

@Service
@RequiredArgsConstructor
public class LendingPersistenceService {
  private final LendingDetailRepo lendingDetailRepo;

  /**
   * Saves a new {@link LendingDetail} from the given {@link Member} and {@link Document}.
   * This method constructs a new {@link LendingDetail} Entity instance
   * from the given {@link Member}, {@link Document} and the {@link LocalDateTime} at the moment,
   * sets up bidirectional relationships, and delegates to {@link #update}.
   *
   * @param member the {@link Member} that wants to lend
   * @param document the {@link Document} that will be lent
   *
   * @throws RuntimeException when unexpected errors occur when working with Database (such as constraints violation)
   *
   * @return the constructed {@link LendingDetail}.
   */
  public LendingDetail lend(@NonNull Member member, @NonNull Document document) {
    LendingDetail lendingDetail = new LendingDetail(LocalDateTime.now());
    member.lendDocument(lendingDetail);
    document.lendDocument(lendingDetail);
    return update(lendingDetail);
  }

  /**
   * Saves a {@link LendingDetail}.
   * <p>
   * If the given {@link LendingDetail} has no {@code id} (or in other words, its {@code id} is {@code NULL}),
   * this method adds a new {@link LendingDetail} to the Database.
   * <br>
   * If the {@link LendingDetail} has an {@code id},
   * this method updates a pre-existing {@link LendingDetail} whose {@code id} is the same as the given LendingDetail's.
   * <p>
   * In the case the given {@link LendingDetail} has an {@code id}, but doesn't match to any existing LendingDetail's,
   * this method's behavior may be unexpected, please refrain from doing this.
   *
   * @apiNote This method returns the saved {@link LendingDetail} Entity instance,
   * which may be different from the given instance and may have different data.
   *
   * @param lendingDetail the {@link LendingDetail} to save
   *
   * @throws RuntimeException when unexpected errors occur when working with Database (such as constraints violation)
   *
   * @return the saved {@link LendingDetail} Entity instance, which may be different from the given instance
   */
  public LendingDetail update(@NonNull LendingDetail lendingDetail) {
    return  lendingDetailRepo.save(lendingDetail);
  }

  /**
   * Deletes the {@link LendingDetail} with the given {@code id}.
   * If the {@link LendingDetail} is not found in the persistence store it is silently ignored.
   *
   * @param id the {@code id} of the {@link LendingDetail} to delete
   */
  public void delete(int id) {
    lendingDetailRepo.deleteById(id);
  }
}
