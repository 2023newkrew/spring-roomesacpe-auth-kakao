package nextstep.auth;

import nextstep.member.Role;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        checkIsValidToken(request, response, accessToken);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void checkIsValidToken(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException {
        if (accessToken == null ||
                accessToken.length() < "Bearer ".length()) {
            request.setAttribute("exception", "AuthenticationException");
            request.getRequestDispatcher("/api/error").forward(request, response);
            return;
        }
        String token = accessToken.substring("Bearer ".length());

        if (!jwtTokenProvider.validateToken(token)
                || jwtTokenProvider.getRole(token) != Role.ADMIN) {
            request.setAttribute("exception", "UnAuthorizationException");
            request.getRequestDispatcher("/api/error").forward(request, response);
        }
    }
}

