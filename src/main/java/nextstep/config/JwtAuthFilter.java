package nextstep.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class JwtAuthFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthContext authContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String accessToken = AuthorizationExtractor.extract(httpRequest);

        if (isGETMethod(httpRequest)) {
            chain.doFilter(httpRequest, response);
            return;
        }

        if (!isValidToken(accessToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        authContext.setAuthMember(jwtTokenProvider.getAuthMember(accessToken));
        chain.doFilter(httpRequest, response);
        authContext.clear();
    }

    private boolean isValidToken(String accessToken) {
        return accessToken != null && jwtTokenProvider.validateToken(accessToken);
    }

    private boolean isGETMethod(HttpServletRequest httpRequest) {
        return HttpMethod.GET.matches(httpRequest.getMethod());
    }
}
