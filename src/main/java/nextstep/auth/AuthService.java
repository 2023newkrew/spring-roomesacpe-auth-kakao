package nextstep.auth;

import nextstep.member.Member;
import nextstep.support.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long validate(Optional<Member> member) {
        return member.orElseThrow( () -> new UnauthorizedAccessException("사용자 정보가 올바르지 않습니다.")).getId();
    }

    public TokenResponse createToken(Long id) {
        return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(id)));
    }

    public Long decodeTokenByRequest(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new UnauthorizedAccessException("토큰이 존재하지 않습니다");
        }
        String token = request.getHeader("Authorization").split(" ")[1];
        return Long.valueOf(jwtTokenProvider.getPrincipal(token));
    }

}
