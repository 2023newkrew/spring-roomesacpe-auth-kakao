package nextstep.auth;

import nextstep.auth.dto.TokenResponse;
import nextstep.common.LoginMember;
import nextstep.member.Member;
import nextstep.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void validateLoginMember(LoginMember loginMember) {
        if (Objects.isNull(loginMember)) {
            throw new UnauthorizedAccessException("인증되지 않은 사용자입니다.");
        }
    }

    public TokenResponse createToken(Member member) {
        String payload = String.valueOf(member.getId());
        String token = jwtTokenProvider.createToken(payload);
        return new TokenResponse(token);
    }


    public String extractToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new UnauthorizedAccessException("토큰이 존재하지 않습니다");
        }
        return request.getHeader("Authorization").split(" ")[1];
    }

    public Long decodeToken(String token) {
        try {
            return Long.valueOf(jwtTokenProvider.getPrincipal(token));
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedAccessException("유효하지 않은 토큰입니다.");
        }
    }

}
