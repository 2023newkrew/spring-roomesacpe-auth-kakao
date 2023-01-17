package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.exceptions.exception.AuthorizationException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractToken(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
        request.setAttribute("accessToken", token);
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            return authorization.split(" ")[1];
        } catch (Exception e) {
            throw new AuthorizationException("Authorization 헤더에 토큰이 존재하지 않습니다.");
        }
    }

}
