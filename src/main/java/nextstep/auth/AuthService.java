package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.MemberNotFoundException;
import nextstep.support.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final static JwtTokenProvider TOKEN_PROVIDER = new JwtTokenProvider();
    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    private Member findByUsername(String username) {
        Member member = memberDao.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }
    private void validatePassword(Member member, String password) {
        if (member.checkWrongPassword(password)) {
            throw new UnauthorizedException();
        }
    }

    public String createToken(TokenRequest tokenRequest) {
        Member member = findByUsername(tokenRequest.getUsername());
        validatePassword(member, tokenRequest.getPassword());
        return TOKEN_PROVIDER.createToken(member.getUsername());
    }

}
