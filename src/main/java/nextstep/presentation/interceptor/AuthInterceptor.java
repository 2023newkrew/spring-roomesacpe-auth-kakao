package nextstep.presentation.interceptor;

import nextstep.utils.JwtTokenProvider;
import nextstep.error.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.error.ErrorType.UNAUTHORIZED_ERROR;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "accessToken";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractToken(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new ApplicationException(UNAUTHORIZED_ERROR);
        }

        request.setAttribute(ACCESS_TOKEN, token);
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            return authorization.split(" ")[1];
        } catch (Exception e) {
            throw new ApplicationException(UNAUTHORIZED_ERROR);
        }
    }

}
