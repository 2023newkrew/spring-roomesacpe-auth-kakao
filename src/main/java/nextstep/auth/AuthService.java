package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse getToken(TokenRequest request) {
        Member member = memberDao.findByUsername(request.getUsername());
        if (member == null || member.checkWrongPassword(request.getPassword())) {
            throw new AuthorizationException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(member.getId().toString()));
    }
}
