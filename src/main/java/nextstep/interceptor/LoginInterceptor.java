package nextstep.interceptor;

import nextstep.domain.auth.AuthService;
import nextstep.persistence.member.Role;
import nextstep.support.NotValidateTokenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader("authorization").split(" ")[1];

        if (accessToken == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        try {
            String principal = authService.getPrincipal(accessToken);
            Role role = Role.valueOf(authService.get(accessToken, "role").toUpperCase());
            request.setAttribute("loginId", Long.parseLong(principal));
            request.setAttribute("role", role);
        }
        catch (NotValidateTokenException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }
}
