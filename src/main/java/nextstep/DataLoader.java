package nextstep;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoader implements ApplicationRunner {

    public static Member ADMIN_MEMBER;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.phone}")
    private String adminPhone;

    @PostConstruct
    void setUp() {
        ADMIN_MEMBER = new Member(adminUsername, adminPassword, adminName, adminPhone, Role.ADMIN);
    }

    private final MemberDao memberDao;

    public DataLoader(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        memberDao.save(ADMIN_MEMBER);
    }
}
