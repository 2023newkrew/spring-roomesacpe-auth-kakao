package nextstep.login;

import nextstep.member.MemberDao;
import nextstep.support.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private MemberDao memberDao;

    public LoginService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public LoginMember findByUsername(String username) {
        return LoginMember.of(
                memberDao.findByUsername(username)
                        .orElseThrow(MemberNotFoundException::new)
        );
    }
}
