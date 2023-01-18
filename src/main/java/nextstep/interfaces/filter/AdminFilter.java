package nextstep.interfaces.filter;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.template.Role;
import nextstep.infra.jwt.AuthorizationExtractor;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.infra.jwt.MemberDetails;

import javax.security.sasl.AuthenticationException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Deprecated
@RequiredArgsConstructor
public class AdminFilter implements Filter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = AuthorizationExtractor.extract((HttpServletRequest) request);
        MemberDetails memberDetails = jwtTokenProvider.getPrincipal(token);

        if (isNotAdmin(memberDetails)) {
            throw new AuthenticationException();
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isNotAdmin(MemberDetails memberDetails) {
        return !memberDetails.getRole().equals(Role.ADMIN);
    }
}
