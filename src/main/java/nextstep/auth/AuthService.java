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

    private void validatePassword(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(MemberNotFoundException::new);
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new UnauthorizedException();
        }
    }

    public String createToken(TokenRequest tokenRequest) {
        validatePassword(tokenRequest);
        return TOKEN_PROVIDER.createToken(tokenRequest.getUsername());
    }

}
