package nextstep.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.annotation.AdminOnly;
import nextstep.auth.annotation.LoginRequired;
import nextstep.exceptions.exception.auth.AuthorizationException;
import nextstep.exceptions.exception.auth.NotAdminException;
import nextstep.exceptions.exception.auth.TokenNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isRequiredAccessToken(handler)) {
            setAccessTokenToRequestAttribute(request);
        }
        if (isAdminOnly(handler)) {
            checkAdmin(request);
        }
        return true;
    }

    private boolean isRequiredAccessToken(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(LoginRequired.class) != null ||
                handlerMethod.getMethod().getDeclaringClass().getAnnotation(AdminOnly.class) != null ||
                handlerMethod.getMethodAnnotation(AdminOnly.class) != null;
    }

    private void setAccessTokenToRequestAttribute(HttpServletRequest request) {
        String token = extractToken(request);
        request.setAttribute("accessToken", token);
    }

    private String extractToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        validateToken(token);
        return token;
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new TokenNotFoundException();
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
    }

    private boolean isAdminOnly(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        return handlerMethod.getMethodAnnotation(AdminOnly.class) != null ||
                handlerMethod.getMethod().getDeclaringClass().getAnnotation(AdminOnly.class) != null;
    }

    private void checkAdmin(HttpServletRequest request) {
        String token = (String) request.getAttribute("accessToken");
        if (!jwtTokenProvider.getRole(token).equals("ADMIN")) {
            throw new NotAdminException();
        }
    }

}
