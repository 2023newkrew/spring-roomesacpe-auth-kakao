package nextstep.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberDaoTest {

    @Autowired
    MemberDao memberDao;

    @DisplayName("멤버를 저장한다")
    @Test
    void save() {
        Member member = new Member("username", "password", "name", "010-1234-5678");

        Long savedId = memberDao.save(member);
        assertThat(savedId).isNotNull();

        Member expected = new Member(
                savedId, "username", "password", "name", "010-1234-5678", Role.USER);

        assertThat(memberDao.findById(savedId)).isEqualTo(expected);
    }

    @DisplayName("id로 멤버를 조회한다")
    @Test
    void findById() {
        Member member = new Member("username", "password", "name", "010-1234-5678");

        Long savedId = memberDao.save(member);

        Member expected = new Member(
                savedId, "username", "password", "name", "010-1234-5678", Role.USER);

        assertThat(memberDao.findById(savedId)).isEqualTo(expected);
    }

    @DisplayName("username 으로 멤버를 조회한다")
    @Test
    void findByUsername() {
        Member member = new Member("username", "password", "name", "010-1234-5678");

        Long savedId = memberDao.save(member);

        Member expected = new Member(
                savedId, "username", "password", "name", "010-1234-5678", Role.USER);

        assertThat(memberDao.findByUsername("username")).isNotEmpty().get()
                .isEqualTo(expected);
    }
}
