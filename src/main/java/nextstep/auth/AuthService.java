package nextstep.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String token = jwtTokenProvider.createToken(tokenRequest.getUsername());
        return new TokenResponse(token);
    }
}
