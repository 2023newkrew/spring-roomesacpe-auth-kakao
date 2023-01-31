package nextstep.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nextstep.global.util.AuthorizationHeaderExtractor;
import nextstep.global.util.AuthorizationHeaderExtractor.TokenType;
import nextstep.global.util.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationHeaderExtractor.extract(request, TokenType.BEARER)
            .orElse("");

        try {
            jwtTokenProvider.validateToken(token);

        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
