package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateUsernamePassword(tokenRequest.getUsername(), tokenRequest.getPassword());

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private void validateUsernamePassword(String username, String password) throws AuthorizationException {
        Member member = memberDao.findByUsername(username);

        if (member == null) {
            throw new AuthorizationException();
        }

        if (!Objects.equals(member.getPassword(), password)) {
            throw new AuthorizationException();
        }
    }

}
