package nextstep.auth;

import nextstep.member.Member;
import nextstep.support.exception.AuthorizationExcpetion;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Map;

import static nextstep.auth.JwtTokenConfig.TOKEN_ROLE_KEY;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(Member member, String tokenPassword) {
        if (member.checkWrongPassword(tokenPassword)) {
            throw new AuthorizationExcpetion(RoomEscapeExceptionCode.AUTHORIZATION_FAIL);
        }
        String token = jwtTokenProvider.createToken(member.getUsername(), Map.of(TOKEN_ROLE_KEY, member.getRole().name()));
        return new TokenResponse(token);
    }
}