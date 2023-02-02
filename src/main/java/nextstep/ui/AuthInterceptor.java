package nextstep.ui;

import nextstep.auth.AuthService;
import nextstep.auth.AuthorizationExtractor;
import nextstep.member.Member;
import nextstep.support.PermissionException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        Member member = authService.getMemberFromToken(token);
        if (!member.isAdmin()) {
            throw new PermissionException();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
