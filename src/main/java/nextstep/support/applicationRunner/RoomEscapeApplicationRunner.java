package nextstep.support.applicationRunner;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomEscapeApplicationRunner implements ApplicationRunner {
    private final MemberDao memberDao;
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;
    @Value("${admin.name}")
    private String name;
    @Value("${admin.phone}")
    private String phone;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member admin = Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
                .role(Role.ADMIN)
                .build();
        memberDao.save(admin);
    }
}
