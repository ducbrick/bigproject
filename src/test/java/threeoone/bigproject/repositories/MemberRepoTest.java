package threeoone.bigproject.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import threeoone.bigproject.entities.Member;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepoTest {
  @Autowired
  private MemberRepo memberRepo;

  @Test
  @DisplayName("Test build & run")
  public void test() {

  }

  @Test
  @DisplayName("Find no members whose names contain a specific string")
  void noMemberWhoseNameContainString() {
    Member m1 = new Member("Bob");
    Member m2 = new Member("John");
    Member m3 = new Member("Ducbrick");
    Member m4 = new Member("Succ");

    m1 = memberRepo.save(m1);
    m2 = memberRepo.save(m2);
    m3 = memberRepo.save(m3);
    m4 = memberRepo.save(m4);

    assertThat(memberRepo.findWithNameContaining("impossible").size()).isEqualTo(0);
  }

  @Test
  @DisplayName("Find members whose names contain a specific string")
  void membersWhoseNamesContainString() {
    Member m1 = new Member("sjdffdBobjsfsdjkh");
    Member m2 = new Member("fdgqwcbObqfdc");
    Member m3 = new Member("FCVfdgcFGCRZuccFCrdf");
    Member m4 = new Member("KJMIjlkmIOjkmsUCCIJKLM");

    m1 = memberRepo.save(m1);
    m2 = memberRepo.save(m2);
    m3 = memberRepo.save(m3);
    m4 = memberRepo.save(m4);

    List <Member> query = memberRepo.findWithNameContaining("bob");

    assertThat(query.size()).isEqualTo(2);
    assertThat(query.contains(m1)).isTrue();
    assertThat(query.contains(m2)).isTrue();

    query = memberRepo.findWithNameContaining("ucc");

    assertThat(query.size()).isEqualTo(2);
    assertThat(query.contains(m3)).isTrue();
    assertThat(query.contains(m4)).isTrue();
  }
}