package nextstep.auth;

import nextstep.member.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider tokenProvider;

    public RoleInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request).orElseThrow(() -> new AuthenticationException("Log in required."));
        if (!tokenProvider.validateToken(token)) {
            throw new AuthenticationException("Token invalid.");
        }
        if (!tokenProvider.getRole(token).equals(Role.ADMIN)) {
            throw new AuthenticationException("Access not allowed.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
