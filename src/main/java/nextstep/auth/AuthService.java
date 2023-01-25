package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.AuthorizationExcpetion;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());

        if (!member.getUsername().equals(tokenRequest.getUsername()) || member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new AuthorizationExcpetion();
        }

        return jwtTokenProvider.createToken(member.getUsername());
    }
}
