package nextstep.admin;

import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.AuthorizationException;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hasAdminToken(request)) {
            return true;
        }
        throw new AuthorizationException();
    }

    private boolean hasAdminToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        if (accessToken == null) {
            return false;
        }
        return jwtTokenProvider.validateAdminToken(accessToken);
    }
}
