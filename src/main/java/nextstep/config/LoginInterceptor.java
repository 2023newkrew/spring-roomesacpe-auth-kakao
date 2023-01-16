package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.AuthorizationExtractor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.support.exception.AuthorizationException;
import nextstep.support.exception.NoSuchTokenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    public static final String AUTHORIZATION = "authorization";

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = authorizationExtractor.extract(request);

        if (token.isEmpty()) {
            throw new NoSuchTokenException();
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }

        return true;
    }

}
