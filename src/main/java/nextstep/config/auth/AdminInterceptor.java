package nextstep.config.auth;

import nextstep.auth.AuthService;
import nextstep.auth.LoginMember;
import nextstep.auth.Role;
import nextstep.support.exception.ForbiddenAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AdminInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginMember loginMember = authService.parseTokenFromRequest(request::getHeader);

        if (loginMember.getRole() != Role.ADMIN) {
            throw new ForbiddenAccessException();
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
