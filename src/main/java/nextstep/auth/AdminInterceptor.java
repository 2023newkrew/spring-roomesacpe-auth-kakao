package nextstep.auth;

import nextstep.auth.util.AuthorizationExtractor;
import nextstep.member.Role;
import nextstep.support.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        String memberId = jwtTokenProvider.getMemberId(token);
        Role role = Role.of(jwtTokenProvider.getRole(token));
        if (memberId == null || role == null || !role.equals(Role.ADMIN)) {
            throw new AuthorizationException();
        }

        return true;
    }
}


