package nextstep.interfaces.interceptor;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.template.Role;
import nextstep.domain.model.template.annotation.NoAuth;
import nextstep.infra.jwt.AuthorizationExtractor;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.infra.exception.auth.AuthorizationException;
import nextstep.infra.exception.auth.NoAccessAuthorityException;
import nextstep.infra.exception.auth.NoSuchTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Handler;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String TOKEN_TYPE = "tokenType";
    private static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final Role role;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (hasNoAuth(handler)) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);

        request.setAttribute(ACCESS_TOKEN, token);
        request.setAttribute(TOKEN_TYPE, BEARER_TYPE);

        isTokenExists(token);
        isTokenValid(token);
        isRoleValid(token);

        return true;
    }

    private void isRoleValid(String token) {
        if(!jwtTokenProvider.validateRole(token, role)) {
            throw new NoAccessAuthorityException();
        }
    }

    private void isTokenValid(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
    }

    private void isTokenExists(String token) {
        if (token.isEmpty()) {
            throw new NoSuchTokenException();
        }
    }

    private boolean hasNoAuth(Object handler) {
        return !ObjectUtils.isEmpty(((HandlerMethod) handler).getMethod().getAnnotation(NoAuth.class));
    }

}
