package nextstep.auth;

import nextstep.auth.jwt.JwtTokenProvider;
import nextstep.exception.AuthErrorCode;
import nextstep.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = AuthorizationExtractor.extractTokenFromRequest();
        if (token == null || jwtTokenProvider.getAuthorization(token).equals("USER")) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }
        request.setAttribute("Authenticated", Boolean.TRUE);
        return true;
    }
}
