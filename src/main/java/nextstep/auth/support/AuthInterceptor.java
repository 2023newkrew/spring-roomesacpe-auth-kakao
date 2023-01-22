package nextstep.auth.support;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
        if (!isLoginRequiredMethod(handler)){
            return true;
        }

        if(!isValidAuthorizationHeader(request)){
            throw new AuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isValidAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        return jwtTokenProvider.validateAuthorizationHeader(authorizationHeader);
    }

    private static boolean isLoginRequiredMethod(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod()
                .getAnnotation(LoginRequired.class);

        return !Objects.isNull(loginRequired);
    }
}
