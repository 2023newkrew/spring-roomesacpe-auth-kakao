package nextstep.auth.authorization;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.config.Messages.*;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    public static final String authorization = "Authorization";
    public static final String bearer = "Bearer ";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(authorization);
        if (accessToken == null) {
            throw new AuthorizationServiceException(EMPTY_TOKEN.getMessage());
        }
        return super.preHandle(request, response, handler);
    }
}