package nextstep.auth;

import nextstep.exception.UnAuthorizationException;
import nextstep.member.Role;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        if (accessToken == null ||
                accessToken.length() < "Bearer ".length()) {
            throw new UnAuthorizationException();
        }
        String token = accessToken.substring("Bearer ".length());

        if (jwtTokenProvider.validateToken(token)
                && jwtTokenProvider.getRole(token) != Role.ADMIN) {
            throw new UnAuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }
}

