package nextstep.auth;

import nextstep.exception.LoginInformationException;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateLoginInformation(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private void validateLoginInformation(String username, String password) {
        if (!memberDao.hasUserWith(username, password)) {
            throw new LoginInformationException();
        }
    }
}
