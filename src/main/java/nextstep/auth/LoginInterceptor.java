package nextstep.auth;

import nextstep.support.AuthorizationException;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpMethod httpMethod;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider, HttpMethod httpMethod) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (!Objects.equals(method, httpMethod.name())) {
            return true;
        }

        String accessToken = AuthorizationExtractor.extract(request);
        boolean validationResult = jwtTokenProvider.validateToken(accessToken);
        if (!validationResult) {
            throw new AuthorizationException();
        }

        return true;
    }
}
