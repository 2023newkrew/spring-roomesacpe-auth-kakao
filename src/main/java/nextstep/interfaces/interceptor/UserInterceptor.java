package nextstep.interfaces.interceptor;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.template.Role;
import nextstep.infra.jwt.AuthorizationExtractor;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.infra.exception.auth.AuthorizationException;
import nextstep.infra.exception.auth.NoSuchTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    private static final String ACCESS_TOKEN = "accessToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = authorizationExtractor.extract(request);
        request.setAttribute(ACCESS_TOKEN, token);

        if (token.isEmpty()) {
            throw new NoSuchTokenException();
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }

        if (!jwtTokenProvider.validateRole(token, Role.USER)) {
            throw new AuthorizationException();
        }

        return true;
    }

}
