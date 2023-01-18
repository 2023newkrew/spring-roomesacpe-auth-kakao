package nextstep;

import lombok.RequiredArgsConstructor;
import nextstep.domain.member.Member;
import nextstep.domain.member.MemberDao;
import nextstep.domain.member.MemberRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminInitializer implements CommandLineRunner {

    private final MemberDao memberDao;

    @Override
    public void run(String... args) throws Exception {
        memberDao.save(new Member("admin", "admin", "어드민", "010-0000-0000", MemberRole.ADMIN));
    }
}
