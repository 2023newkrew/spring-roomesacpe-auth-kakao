package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.ErrorCode;
import nextstep.exception.RoomEscapeException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final String BEARER = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("authorization");

        if(accessToken == null){
            throw new RoomEscapeException(ErrorCode.NOT_AUTHENTICATED);
        }
        accessToken = accessToken.substring(BEARER.length());
        if(!jwtTokenProvider.validateToken(accessToken)){
            throw new RoomEscapeException(ErrorCode.INVALID_TOKEN);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
