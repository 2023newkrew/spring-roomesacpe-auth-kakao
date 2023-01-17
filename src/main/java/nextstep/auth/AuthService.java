package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getUsername(), tokenRequest.getPassword())) {
            throw new AuthorizationException();
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(accessToken);
    }

    public boolean checkInvalidLogin(String username, String password) {
        Member member = memberDao.findByUsername(username);
        return member.checkWrongPassword(password);
    }
}
