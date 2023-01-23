package nextstep.auth;

import nextstep.member.Member;
import nextstep.support.exception.RoomEscapeException;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(Member member, String tokenPassword) {
        if (member.checkWrongPassword(tokenPassword)) {
            throw new RoomEscapeException(RoomEscapeExceptionCode.AUTHORIZATION_FAIL);
        }
        String token = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(token);
    }
}
