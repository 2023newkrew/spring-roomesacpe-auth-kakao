package nextstep.auth;

import nextstep.member.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(Member member) {
        String token = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(token);
    }
}
