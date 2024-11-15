package threeoone.bigproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * ORM Entity representing a member of the library.
 * <p>
 * A {@link Member} has a {@link #name}, a {@link #phoneNumber}, an {@link #address}, an {@link #email}
 * and can lend a number of {@link Document},
 * whose detail is represented by {@link #lendingDetails}.
 * <p>
 * {@link #lendingDetails} is a bidirectional one-to-many relationship between {@link Member} and {@link LendingDetail}.
 * JPA requires synchronization on both sides in order to persist.
 * {@link #lendDocument(LendingDetail)}} sets the relationship on both sides.
 * Lombok-generated {@link LendingDetail#setMember(Member)} only sets the relationship on the {@link LendingDetail} side.
 *
 * @author DUCBRICK
 */
@Entity
@Table(name = "member")
@NoArgsConstructor @Getter @Setter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  private String name;

  @NonNull
  @Column(name = "phone_number")
  private String phoneNumber;

  @NonNull
  private String address;

  @NonNull
  private String email;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List <LendingDetail> lendingDetails = new ArrayList <> ();

  public Member(@NonNull String name) {
    this.name = name;
  }

  /**
   * Adds a {@link LendingDetail} to the list of the Member's lent Documents.
   * JPA requires synchronization on both sides in order to persist,
   * meaning a {@link LendingDetail} must exist in its member's {@link #lendingDetails} list.
   * This method sets the relationship on both the {@link Member} side and {@link LendingDetail} side.
   *
   * @param lendingDetail detail about lent {@link Document}
   */
  public void lendDocument(LendingDetail lendingDetail) {
    lendingDetails.add(lendingDetail);
    lendingDetail.setMember(this);
  }
}
