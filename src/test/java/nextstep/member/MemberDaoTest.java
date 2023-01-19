package nextstep.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class MemberDaoTest {
    public static final Member MEMBER = new Member("username", "password", "name", "010-1234-5678");

    @Autowired
    private MemberDao memberDao;


    @Test
    @DisplayName("멤버 생성 테스트")
    void saveTest() {
        assertThatCode(() -> memberDao.save(MEMBER)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("id로 멤버 조회 테스트")
    void findByIdTest(){
        Long saveMemberId = memberDao.save(MEMBER);
        Member foundMember = memberDao.findById(saveMemberId);
        assertThat(foundMember.getUsername()).isEqualTo(MEMBER.getUsername());
        assertThat(foundMember.getPassword()).isEqualTo(MEMBER.getPassword());
        assertThat(foundMember.getName()).isEqualTo(MEMBER.getName());
        assertThat(foundMember.getPhone()).isEqualTo(MEMBER.getPhone());
        assertThat(foundMember.getRole()).isEqualTo(MEMBER.getRole());
    }

    @Test
    @DisplayName("username으로 멤버 조회 테스트")
    void findByUsernameTest(){
        Long saveMemberId = memberDao.save(MEMBER);
        assertThat(memberDao.findByUsername(MEMBER.getUsername()).get()
                .getId())
                .isEqualTo(saveMemberId);
    }

    @Test
    @DisplayName("username과 password로 멤버 조회 테스트")
    void findByUsernameAndPasswordTest() {
        Long saveMemberId = memberDao.save(MEMBER);
        assertThat(memberDao.findByUsernameAndPassword(MEMBER.getUsername(), MEMBER.getPassword())
                .getId())
                .isEqualTo(saveMemberId);
    }
}
