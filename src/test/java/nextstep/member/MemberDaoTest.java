package nextstep.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@JdbcTest
public class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("Member를 저장하여 ID를 반환받을 수 있다.")
    @Test
    void save() {
        // given
        Member member = new Member("username", "password", "name", "phone");

        // when
        Long memberId = memberDao.save(member);

        // then
        assertThat(memberId).isNotNull();
    }

    @DisplayName("Member를 저장시, 고유한 이름이 아니라면 예외가 발생한다")
    @Test
    void saveException() {
        // given
        String name = "joel";
        Member member1 = new Member("username", "password", name, "phone");
        memberDao.save(member1);
        Member member2 = new Member("username2", "password2", name, "phone2");

        // when
        assertThatThrownBy(() -> memberDao.save(member2))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @DisplayName("username으로 알맞은 멤버를 찾을 수 있다.")
    @Test
    void findByUsername() {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        String phone = "phone";
        Member member = new Member(username, password, name, phone);
        memberDao.save(member);

        // when
        Member foundMember = memberDao.findByUsername(username);

        // then
        assertThat(foundMember.getUsername()).isEqualTo(username);
        assertThat(foundMember.getPassword()).isEqualTo(password);
        assertThat(foundMember.getName()).isEqualTo(name);
        assertThat(foundMember.getPhone()).isEqualTo(phone);
    }

    @DisplayName("username과 password를 통해 알맞은 멤버를 찾을 수 있다.")
    @Test
    void findByUsernameAndPassword() {
        // given
        String username = "username";
        String password = "password";
        String name = "name";
        String phone = "phone";
        Member member = new Member(username, password, name, phone);
        memberDao.save(member);

        // when
        Member foundMember = memberDao.findByUsernameAndPassword(username, password);

        // then
        assertThat(foundMember.getUsername()).isEqualTo(username);
        assertThat(foundMember.getPassword()).isEqualTo(password);
        assertThat(foundMember.getName()).isEqualTo(name);
        assertThat(foundMember.getPhone()).isEqualTo(phone);
    }

    @DisplayName("username과 password를 통해 조회된 멤버가 없으면 null을 반환한다.")
    @Test
    void findByUsernameAndPasswordNotFound() {
        // given
        Member member = new Member("username", "password", "name", "phone");
        memberDao.save(member);

        // when
        Member foundMember = memberDao.findByUsernameAndPassword("invalidUsername", "invalidPassword");

        // then
        assertThat(foundMember).isNull();
    }
}