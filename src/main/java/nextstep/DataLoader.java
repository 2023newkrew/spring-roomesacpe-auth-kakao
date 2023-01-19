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

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.phone}")
    private String adminPhone;


    private final MemberDao memberDao;

    public DataLoader(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void run(ApplicationArguments args) {
        Member adminMember = new Member(adminUsername, adminPassword, adminName, adminPhone, Role.ADMIN);
        memberDao.save(adminMember);
    }
}
