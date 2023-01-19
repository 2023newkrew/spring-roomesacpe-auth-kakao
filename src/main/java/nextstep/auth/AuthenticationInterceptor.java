package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.member.MemberService;
import nextstep.support.AuthenticationException;
import nextstep.util.AuthenticationUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = AuthenticationUtil.extractToken(authorization);
        validateAuthentication(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateAuthentication(String token) {
        if (token == null) {
            throw new AuthenticationException();
        }
        if (!validateToken(token)) {
            throw new AuthenticationException();
        }
    }

    private boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
