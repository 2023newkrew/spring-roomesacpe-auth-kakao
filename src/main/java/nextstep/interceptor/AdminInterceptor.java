package nextstep.interceptor;

import nextstep.domain.auth.AuthService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AdminInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        authService.get(request.getHeader("authorization").split(" ")[1], "role");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
