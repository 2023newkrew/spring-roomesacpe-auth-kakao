package nextstep.auth;

import nextstep.infrastructure.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;
import nextstep.interfaces.AuthorizationException;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }


    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername());
        if (member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(member.getId().toString());
        return new TokenResponse(accessToken);
    }

}
