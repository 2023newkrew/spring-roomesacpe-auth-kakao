package nextstep.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthMemberDTO;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class AdminFilter implements Filter {

    private final AuthContext authContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (isGETMethod(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        AuthMemberDTO authMemberDTO = authContext.getAuthMember();

        if (!authMemberDTO.isIsAdmin()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        chain.doFilter(request, response);
    }

    private boolean isGETMethod(HttpServletRequest httpRequest) {
        return HttpMethod.GET.matches(httpRequest.getMethod());
    }
}
