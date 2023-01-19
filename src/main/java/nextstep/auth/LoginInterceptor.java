package nextstep.auth;

import nextstep.support.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String GET_METHOD = "GET";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isGetMethod(request) || hasAuthorizationToken(request)) {
            return true;
        }
        throw new AuthorizationException();
    }

    private boolean isGetMethod(HttpServletRequest request) {
        String method = request.getMethod();
        return GET_METHOD.equalsIgnoreCase(method);
    }

    private boolean hasAuthorizationToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        return accessToken != null;
    }
}
