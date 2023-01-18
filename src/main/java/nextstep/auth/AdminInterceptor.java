package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import nextstep.member.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var bearerToken = request.getHeader("authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new AuthenticationException(ErrorCode.NO_TOKEN);
        }

        var accessToken = bearerToken.substring(7);

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        if (!jwtTokenProvider.getRole(accessToken).equals(Role.ADMIN)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }
}
