package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = TokenExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        request.setAttribute("id", jwtTokenProvider.getPrincipal(accessToken));
        request.setAttribute("role", jwtTokenProvider.getRole(accessToken));

        return true;
    }
}
