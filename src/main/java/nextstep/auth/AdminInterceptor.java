package nextstep.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN_NAME = "authorization";

    private final JwtTokenProvider provider;

    public AdminInterceptor(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String accessToken = getAccessToken(request);
        validateAdmin(accessToken);

        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN_NAME);
        
        return provider.getValidToken(bearerToken);
    }

    private void validateAdmin(String accessToken) {
        TokenData tokenData = provider.getTokenData(accessToken);
        if (!Objects.equals(tokenData.getRole(), "ADMIN")) {
            throw new AuthenticationException();
        }
    }
}
