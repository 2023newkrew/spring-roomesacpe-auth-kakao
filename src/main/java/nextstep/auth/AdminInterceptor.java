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
        String accessToken = TokenExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(accessToken) || !jwtTokenProvider.getRole(accessToken).equals(Role.ADMIN)) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }
}
