package threeoone.bigproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * ORM Entity representing detail about a lent {@link Document}.
 * A {@link LendingDetail} has a {@link #lendTime}, representing the timestamp when the Document was lent.
 * <p>
 * {@link #member} is a bidirectional many-to-one relationship between {@link Member} and {@link LendingDetail}.
 * JPA requires synchronization on both sides in order to persist.
 * {@link Member#lendDocument(LendingDetail)}} sets the relationship on both sides.
 * Lombok-generated {@link #setMember(Member)} only sets the relationship on the {@link LendingDetail} side.
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "LendingDetail")
@NoArgsConstructor @Getter @Setter
public class LendingDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "lend_time")
  @NonNull
  private LocalDateTime lendTime;

  @JoinColumn(name = "member_id")
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Member member;

  @JoinColumn(name = "document_id")
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Document document;

  /**
   * Constructs detail about a new lending of a {@link Document}
   *
   * @param lendTime the timestamp the {@link Document} is lent
   */
  public LendingDetail(@NonNull LocalDateTime lendTime) {
    this.lendTime = lendTime;
  }
}
