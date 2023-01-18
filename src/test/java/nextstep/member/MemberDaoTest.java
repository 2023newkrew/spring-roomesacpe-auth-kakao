package nextstep.member;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class MemberDaoTest {
    public static final Member MEMBER = new Member("username", "password", "name", "010-1234-5678");
    @Autowired
    private MemberDao memberDao;

    @Test
    @DisplayName("username과 password로 멤버 조회 테스트")
    void findByUsernameAndPasswordTest() {
        Long saveMemberId = memberDao.save(MEMBER);
        assertThat(memberDao.findByUsernameAndPassword(MEMBER.getUsername(), MEMBER.getPassword())
                .getId())
                .isEqualTo(saveMemberId);
    }
}
