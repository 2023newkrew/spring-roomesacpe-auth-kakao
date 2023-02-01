package nextstep.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class MemberDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MemberDao memberDao;
    private Member member = Member.builder()
            .name("name")
            .username("username")
            .phone("phone_number")
            .password("password")
            .build();

    @BeforeEach
    void setUp() {
        memberDao = new MemberDao(jdbcTemplate);
    }


    @Test
    void 멤버를_저장하면_아이디를_반환한다() {
        assertThat(memberDao.save(member)).isInstanceOf(Long.class);
    }

    @Test
    void 아이디를_통해_멤버를_조회할_수_있다() {
        Long id = memberDao.save(member);
        assertThat(memberDao.findById(id).get()).isInstanceOf(Member.class);
    }

    @Test
    void 없는_아이디를_조회하면_빈_객체를_반환한다() {
        assertThat(memberDao.findById(100L)).isEqualTo(Optional.empty());
    }

    @Test
    void 닉네임으로_조회할_수_있다() {
        memberDao.save(member);
        assertThat(memberDao.findByUsername(member.getUsername()).get())
                .isInstanceOf(Member.class);
    }

    @Test
    void 없는_닉네임으로_조회하면_빈_객체가_반환된다() {
        assertThat(memberDao.findByUsername("notExistName"))
                .isEqualTo(Optional.empty());
    }
}
