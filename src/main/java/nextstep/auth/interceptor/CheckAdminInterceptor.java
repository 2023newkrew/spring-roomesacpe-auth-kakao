package nextstep.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.service.AuthService;
import nextstep.exception.UnauthorizedAccessException;
import nextstep.member.MemberRole;
import nextstep.member.dto.LoginMember;
import org.springframework.web.servlet.HandlerInterceptor;

public class CheckAdminInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public CheckAdminInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!MemberRole.isAdmin(authService.decodeTokenByRequest(request).getRole())) {
            throw new UnauthorizedAccessException("접근 권한이 없습니다.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
