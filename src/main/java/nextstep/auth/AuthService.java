package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Long validateAndGetMemberId(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member == null) {
            throw new NotExistEntityException();
        }
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new IllegalArgumentException();
        }
        return member.getId();
    }
}
