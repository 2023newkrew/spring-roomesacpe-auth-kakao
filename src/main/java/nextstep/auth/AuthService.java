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
        Member member = memberDao.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (member == null) {
            throw new AuthorizationException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(
                member.getId().toString(),
                member.getRole().value())
        );
    }
}
