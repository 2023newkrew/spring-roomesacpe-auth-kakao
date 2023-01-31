package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse createToken(Member member) {
        String token = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(token);
    }
}