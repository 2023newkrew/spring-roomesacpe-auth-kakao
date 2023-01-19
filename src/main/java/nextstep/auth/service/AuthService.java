package nextstep.auth.service;

import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.auth.utils.JwtTokenProvider;
import nextstep.exception.UnauthorizedAccessException;
import nextstep.member.Member;
import nextstep.member.dto.LoginMember;
import org.springframework.stereotype.Service;

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

    public TokenResponse createToken(Optional<Member> member) {
        return new TokenResponse(jwtTokenProvider.createToken(validate(member)));
    }

    public LoginMember decodeTokenByRequest(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new UnauthorizedAccessException("토큰이 존재하지 않습니다");
        }
        try {
            return jwtTokenProvider.getPrincipal(request.getHeader("Authorization").split(" ")[1]);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedAccessException("유효하지 않은 토큰입니다.");
        }
    }

    private Member validate(Optional<Member> member) {
        return member.orElseThrow(() -> new UnauthorizedAccessException("사용자 정보가 올바르지 않습니다."));
    }

    public void validateId(Long expectedId, Long actualId) {
        if(!expectedId.equals(actualId)){
            throw new UnauthorizedAccessException("Id 정보가 일치하지 않습니다");
        }
    }
}
