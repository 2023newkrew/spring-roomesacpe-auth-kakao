package nextstep.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
import org.springframework.http.HttpMethod;

public class JwtAuthFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String accessToken = AuthorizationExtractor.extract(httpRequest);

        if (!isGETMethod(httpRequest) && !isValidToken(accessToken)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        chain.doFilter(httpRequest, response);
    }

    private boolean isValidToken(String accessToken) {
        return accessToken != null && jwtTokenProvider.validateToken(accessToken);
    }

    private boolean isGETMethod(HttpServletRequest httpRequest) {
        return HttpMethod.GET.matches(httpRequest.getMethod());
    }
}
