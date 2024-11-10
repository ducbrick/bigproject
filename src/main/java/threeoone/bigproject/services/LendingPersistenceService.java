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
import threeoone.bigproject.repositories.DocumentRepo;
import threeoone.bigproject.repositories.LendingDetailRepo;
import threeoone.bigproject.repositories.MemberRepo;

@Service
@RequiredArgsConstructor
public class LendingPersistenceService {
  private final LendingDetailRepo lendingDetailRepo;
  private final MemberRepo memberRepo;
  private final DocumentRepo documentRepo;

  /**
   * Saves a new {@link LendingDetail} from the given {@code memberId} and {@code documentId}.
   * This method constructs a new {@link LendingDetail} Entity instance
   * from the given {@code memberId}, {@code documentId}, the {@link LocalDateTime} at the moment,
   * , sets up bidirectional relationships and saves it to the Database.
   *
   * @param memberId the {@code id} of the lending {@link Member}
   * @param documentId the {@code id} of the lent {@link Document}
   *
   * @throws IllegalArgumentException when no {@link Document} or {@link Member} with the given {@code id} exists
   * @throws RuntimeException when unexpected errors occur when working with Database (such as constraints violation)
   *
   * @return the constructed {@link LendingDetail}.
   */
  public LendingDetail lend(int memberId, int documentId) {
    Member member = memberRepo.findById(memberId).orElse(null);

    if (member == null) {
      throw new IllegalArgumentException("No Member with that id exists");
    }

    Document document = documentRepo.findById(documentId).orElse(null);

    if (document == null) {
      throw new IllegalArgumentException("No Document with that id exists");
    }

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
  private LendingDetail update(@NonNull LendingDetail lendingDetail) {
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
