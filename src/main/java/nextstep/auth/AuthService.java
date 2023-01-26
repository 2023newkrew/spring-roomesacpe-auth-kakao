package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.exception.LoginFailException;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateLoginInformation(tokenRequest.getUsername(), tokenRequest.getPassword());
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    private void validateLoginInformation(String username, String password) {
        if (!memberDao.countByUsernameAndPassword(username, password)) {
            throw new LoginFailException();
        }
    }
}
