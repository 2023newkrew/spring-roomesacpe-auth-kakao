package nextstep.auth.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {
    public static final String authorization = "Authorization";
    public static final String bearer = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TokenVerifier.validateToken(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}