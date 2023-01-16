package nextstep.config;

import nextstep.support.exception.AuthorizationException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    public static final String AUTHORIZATION = "authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(AUTHORIZATION);

        if(accessToken == null){
            throw new AuthorizationException();
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
