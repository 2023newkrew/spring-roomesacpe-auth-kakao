package nextstep.auth;

import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    public static final String AUTHORIZATION = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new AuthorizationException();
        }
        String credential = jwtTokenProvider.getCredential(authorizationHeader);
        if (!jwtTokenProvider.validateToken(credential)) {
            throw new AuthorizationException();
        }
        return super.preHandle(request, response, handler);
    }
}
