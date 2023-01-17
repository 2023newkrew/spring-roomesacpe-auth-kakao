package nextstep.login;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.MemberRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private MemberDao memberDao;

    public LoginService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long create(MemberRequest memberRequest) {
        return memberDao.save(memberRequest.toEntity());
    }

    public Member findByUsername(String username) {
        return memberDao.findByUsername(username);
    }
}
