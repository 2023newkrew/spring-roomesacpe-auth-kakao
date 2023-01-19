package nextstep.auth;

import nextstep.auth.jwt.JwtTokenProvider;
import nextstep.exception.AuthErrorCode;
import nextstep.exception.BusinessException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Authorization authorization = ((HandlerMethod) handler).getMethodAnnotation(Authorization.class);
        if (authorization == null) {
            return true;
        }

        String token = AuthorizationExtractor.extractTokenFromRequest();
        if (token == null || jwtTokenProvider.getAuthorization(token).equals("USER")) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }
        return true;
    }
}
