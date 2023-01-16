package nextstep.config;

import nextstep.support.AuthorizationException;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        if(!HttpMethod.GET.matches(request.getMethod()) && accessToken == null) {
            throw new AuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }
}