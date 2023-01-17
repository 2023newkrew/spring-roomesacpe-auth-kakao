package nextstep.config;

import nextstep.exception.ErrorCode;
import nextstep.exception.RoomEscapeException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("authorization");
        if(accessToken == null){
            throw new RoomEscapeException(ErrorCode.NOT_AUTHENTICATED);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
