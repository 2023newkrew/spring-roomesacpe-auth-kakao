package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import nextstep.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class PrincipalExtractor {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public PrincipalExtractor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public long extract(HttpServletRequest request) {
        String token = extractToken(request);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }
        return Long.parseLong(jwtTokenProvider.getPrincipal(token));
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationValue == null || !authorizationValue.startsWith(BEARER_TOKEN_PREFIX)) {
            throw new UnauthorizedException();
        }

        return authorizationValue.substring(BEARER_TOKEN_PREFIX.length());
    }
}