package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.AuthorizationExtractor;
import nextstep.infrastructure.JwtTokenProvider;
import nextstep.support.exception.AuthorizationException;
import nextstep.support.exception.NoSuchTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private static final String ACCESS_TOKEN = "accessToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = authorizationExtractor.extract(request);
        request.setAttribute(ACCESS_TOKEN, token);

        if (token.isEmpty()) {
            throw new NoSuchTokenException();
        }

        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }

        return true;
    }

}
