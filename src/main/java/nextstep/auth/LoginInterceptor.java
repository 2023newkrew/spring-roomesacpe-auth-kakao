package nextstep.auth;

import nextstep.support.UnauthorizedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String headerValue = AuthorizationExtractor.extract(request);
        if (headerValue == null) {
            throw new UnauthorizedException("토큰이 존재하지 않습니다.");
        }
        try {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

            String id = jwtTokenProvider.getPrincipal(headerValue);
            String userlevel = jwtTokenProvider.getIssuer(headerValue);
            if (id == null || userlevel == null) {
                throw new UnauthorizedException("토큰에 값이 존재하지 않습니다.");
            }
            if (!userlevel.equals("admin")){
                throw new UnauthorizedException("권한이 없습니다.");
            }
        }
        catch (RuntimeException ex){
            throw new UnauthorizedException("잘못된 토큰입니다.");
        }

        return super.preHandle(request, response, handler);
    }
}
