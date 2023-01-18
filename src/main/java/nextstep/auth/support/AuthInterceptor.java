package nextstep.auth.support;

import lombok.RequiredArgsConstructor;
import nextstep.support.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {
    public static final String AUTHORIZATION = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);

        if(Objects.isNull(loginRequired)){
            return true;
        }

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
