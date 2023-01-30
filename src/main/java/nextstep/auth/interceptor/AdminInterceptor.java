package nextstep.auth.interceptor;

import nextstep.auth.domain.AccessToken;
import nextstep.auth.domain.Role;
import nextstep.global.exception.NotAuthorizedException;
import nextstep.global.exception.UnauthenticatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        AccessToken accessToken = AccessToken.from(extractToken(authorization));

        Role role = accessToken.getRole();
        if (!Objects.equals(role, Role.ADMIN)) {
            throw new NotAuthorizedException();
        }

        return true;
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(v -> v.split("Bearer ")[1])
                .orElseThrow(UnauthenticatedException::new);
    }
}
