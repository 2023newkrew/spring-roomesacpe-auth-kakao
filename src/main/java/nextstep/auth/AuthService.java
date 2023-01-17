package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.member.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createAccessToken(Member member, String password) {
        if (member.checkWrongPassword(password)) {
            throw new RoomReservationException(ErrorCode.INVALID_PASSWORD);
        }
        return jwtTokenProvider.createToken(member.getId().toString());
    }

    public String getPrincipal(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new RoomReservationException(ErrorCode.INVALID_TOKEN);
        }
        return jwtTokenProvider.getPrincipal(accessToken);
    }
}
