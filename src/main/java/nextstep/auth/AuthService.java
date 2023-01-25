package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import org.springframework.stereotype.Service;

import java.util.Map;

import static nextstep.auth.JwtTokenConfig.TOKEN_ROLE_KEY;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse createToken(Member member) {
        String token = jwtTokenProvider.createToken(member.getUsername(), Map.of(TOKEN_ROLE_KEY, member.getRole().name()));
        return new TokenResponse(token);
    }
}