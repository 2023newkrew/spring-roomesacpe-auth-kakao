package nextstep.interceptor;

import nextstep.domain.auth.AuthService;
import nextstep.persistence.member.Role;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AdminInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        String accessToken = request.getHeader("authorization").split(" ")[1];

        Role role = Role.valueOf(authService.get(accessToken, "role").toUpperCase());
        if (role != Role.ADMIN) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }
}
