package nextstep.auth;

import io.jsonwebtoken.Claims;
import nextstep.auth.dto.TokenResponse;
import nextstep.common.LoginMember;
import nextstep.common.Role;
import nextstep.exception.ForbiddenException;
import nextstep.member.Member;
import nextstep.exception.UnauthorizedAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(Member member) {
        String token = jwtTokenProvider.createToken(member.getId(), member.getRole());
        return new TokenResponse(token);
    }

    public Boolean isValid(HttpServletRequest request) {
        String token = extractToken(request);
        if (!jwtTokenProvider.isValidToken(token)) {
            throw new UnauthorizedAccessException("유효하지 않은 토큰입니다.");
        }
        return true;
    }

    public Boolean isAdmin(HttpServletRequest request) {
        String token = extractToken(request);
        LoginMember loginMember = decodeToken(token);

        System.out.println(loginMember.getRole());
        System.out.println(Role.ADMIN.equals(loginMember.getRole()));
        if (!Role.ADMIN.equals(loginMember.getRole())) {
            throw new ForbiddenException("권한이 부족합니다.");
        }
        return true;
    }

    public String extractToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new UnauthorizedAccessException("토큰이 존재하지 않습니다");
        }
        return request.getHeader("Authorization").split(" ")[1];
    }

    public LoginMember decodeToken(String token) {
        try {
            Claims claims = jwtTokenProvider.getClaims(token);
            Long id = claims.get("id", Long.class);
            Role role = Role.valueOf(claims.get("role", String.class));
            return new LoginMember(id, role);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedAccessException("유효하지 않은 토큰입니다.");
        }
    }
}
