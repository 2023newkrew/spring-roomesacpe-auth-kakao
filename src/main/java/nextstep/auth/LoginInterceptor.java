package nextstep.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = extractToken(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("인증되지 않은 사용자입니다.");
        }

        request.setAttribute("accessToken", token);
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            return authorization.split(" ")[1];
        } catch (Exception e) {
            throw new RuntimeException("인증되지 않은 사용자입니다.");
        }
    }

}
