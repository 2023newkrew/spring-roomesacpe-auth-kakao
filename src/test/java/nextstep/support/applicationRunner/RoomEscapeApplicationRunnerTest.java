package nextstep.support.applicationRunner;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class RoomEscapeApplicationRunnerTest {
    @Autowired
    private MemberDao memberDao;

    @Value("${admin.username}")
    private String adminUsername;

    @Test
    @DisplayName("앱 실행시 어드민 계정이 등록된다.")
    void adminTest() {
        Optional<Member> adminOptional = memberDao.findByUsername(adminUsername);
        Assertions.assertThat(adminOptional.isPresent()).isTrue();

        Member admin = adminOptional.get();
        Assertions.assertThat(admin.getRole()).isEqualTo(Role.ADMIN);
    }
}