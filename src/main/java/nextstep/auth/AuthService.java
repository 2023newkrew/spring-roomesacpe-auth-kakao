package nextstep.auth;

import nextstep.auth.dto.TokenResponse;
import nextstep.member.dto.LoginMember;
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

    private Long validate(Optional<Member> member) {
        return member.orElseThrow(() -> new UnauthorizedAccessException("사용자 정보가 올바르지 않습니다.")).getId();
    }

    public void validateLoginMember(LoginMember loginMember) {
        if (Objects.isNull(loginMember)) {
            throw new UnauthorizedAccessException("인증되지 않은 사용자입니다.");
        }
    }

    public TokenResponse createToken(Optional<Member> member) {
        Long id = validate(member);
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(id)));
    }

    public Long decodeTokenByRequest(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new UnauthorizedAccessException("토큰이 존재하지 않습니다");
        }
        String token = request.getHeader("Authorization").split(" ")[1];
        try {
            return Long.valueOf(jwtTokenProvider.getPrincipal(token));
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedAccessException("유효하지 않은 토큰입니다.");
        }
    }

}
