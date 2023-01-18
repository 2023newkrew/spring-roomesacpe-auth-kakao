package nextstep.auth;

import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthValidator authValidator;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        authValidator = new AuthValidator(memberDao);
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Long id = authValidator.validateUser(tokenRequest.getUsername(), tokenRequest.getPassword());
        return new TokenResponse(jwtTokenProvider.createToken(id.toString(), null));
    }
}
