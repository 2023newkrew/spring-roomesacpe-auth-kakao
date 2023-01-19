package nextstep.sql;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static nextstep.member.Member.ADMIN_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpringBootInitialLoadIntegrationTest {

    @Autowired
    private MemberDao memberDao;

    @Test
    @DisplayName("애플리케이션이 동작 할 때 admin member가 default로 들어가야 한다.")
    void should_getAdminMember_when_initializeSpring() {
        Member admin = memberDao.findByUsername(ADMIN_USERNAME).orElseThrow();

        assertThat(admin.getName()).isEqualTo("admin");
        assertThat(admin.getUsername()).isEqualTo("admin");
        assertThat(admin.getPassword()).isEqualTo("admin");
    }
}
