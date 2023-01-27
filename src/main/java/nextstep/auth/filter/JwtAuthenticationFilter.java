package nextstep.auth.filter;

import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthenticationProvider;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.LoginMemberContextHolder;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.jwt.DecodedJwtToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationProvider authenticationProvider;

    private final LoginMemberContextHolder loginMemberContextHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rawToken = AuthorizationExtractor.extract(request);
        LoginMember loginMember = authenticationProvider.authenticate(
                new DecodedJwtToken(rawToken)
        );
        loginMemberContextHolder.setContext(loginMember);

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loginMemberContextHolder.clearContext();
        }
    }
}
