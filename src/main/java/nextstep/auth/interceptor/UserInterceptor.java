package nextstep.auth.interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.exception.auth.AuthErrorCode;
import nextstep.exception.auth.AuthException;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.auth.role.Role.ROLE_USER;

public class UserInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public UserInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        if (HttpMethod.GET.name().equals(method)) {
            return true;
        }
        String accessToken = jwtTokenProvider.resolveToken(request);
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthException(AuthErrorCode.TOKEN_NOT_AVAILABLE);
        }
        if (hasUserRole(accessToken)) {
            return true;
        }
        throw new AuthException(AuthErrorCode.ACCESS_DENIED);
    }

    private boolean hasUserRole(String accessToken) {
        return jwtTokenProvider.getRoles(accessToken)
                .stream()
                .anyMatch(role -> role.equals(ROLE_USER.name()));
    }
}
