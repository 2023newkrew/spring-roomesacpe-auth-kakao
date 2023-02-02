package nextstep.auth.interceptor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.annotation.AdminOnly;
import nextstep.auth.annotation.LoginRequired;
import nextstep.exceptions.exception.auth.AuthorizationException;
import nextstep.exceptions.exception.auth.ForbiddenException;
import nextstep.exceptions.exception.auth.TokenNotFoundException;
import nextstep.member.Role;
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
        getRoleIfRequiredAuthorization(handler).ifPresent(
                requiredRole -> {
                    setAccessTokenToRequestAttribute(request);
                    checkRoleFromRequest(request, requiredRole);
                }
        );
        return true;
    }

    private Optional<Role> getRoleIfRequiredAuthorization(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        return Stream.of(handlerMethod.getMethodAnnotation(LoginRequired.class),
                        handlerMethod.getMethodAnnotation(AdminOnly.class),
                        handlerMethod.getMethod().getDeclaringClass().getAnnotation(LoginRequired.class),
                        handlerMethod.getMethod().getDeclaringClass().getAnnotation(AdminOnly.class)
                )
                .filter(Objects::nonNull)
                .map(annotation -> ((LoginRequired) annotation).requiredRole())
                .findFirst();
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

    private void checkRoleFromRequest(HttpServletRequest request, Role requiredRole) {
        String token = (String) request.getAttribute("accessToken");
        Role loginMemberRole = Role.from(jwtTokenProvider.getRole(token));
        if (!loginMemberRole.isAvailable(requiredRole)) {
            throw new ForbiddenException();
        }
    }

}
