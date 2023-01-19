package nextstep.auth;

import nextstep.auth.util.AuthorizationExtractor;
import nextstep.support.AuthorizationException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    private JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        String memberId = jwtTokenProvider.getMemberId(token);
        String role = jwtTokenProvider.getRole(token);
        if (memberId == null || role == null || !role.equals("ADMIN")) {
            throw new AuthorizationException();
        }

        return true;
    }
}


