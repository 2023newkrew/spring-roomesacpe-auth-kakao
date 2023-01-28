package nextstep.auth.Interceptor;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.support.Messages.*;

public class LoginInterceptor implements HandlerInterceptor {
    public static final String authorization = "Authorization";
    public static final String bearer = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(authorization);
        if (accessToken == null) {
            throw new AuthorizationServiceException(EMPTY_TOKEN.getMessage());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}