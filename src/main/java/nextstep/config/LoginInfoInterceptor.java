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
public class LoginInfoInterceptor implements HandlerInterceptor {
    private static final String ACCESS_TOKEN = "accessToken";
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        authorizationExtractor.extract(request);
        request.setAttribute(ACCESS_TOKEN, authorizationExtractor.extract(request));

        return true;
    }
}
