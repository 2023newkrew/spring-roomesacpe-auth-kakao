package nextstep.config;

import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.Authority;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AdminIntercepter is the interceptor that handles requests which requires admin authority.
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
    JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = AuthorizationExtractor.extract(request);
        if (accessToken == null){
            throw new AuthorizationException();
        }

        Authority authority = Authority.valueOf(jwtTokenProvider.getAuthority(accessToken));
        if (!Authority.ADMIN.equals(authority)){
            throw new AuthorizationException();
        }
        return super.preHandle(request, response, handler);
    }
}
