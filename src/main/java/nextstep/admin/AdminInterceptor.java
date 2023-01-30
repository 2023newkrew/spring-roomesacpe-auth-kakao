package nextstep.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.JwtTokenProvider;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider, AdminService adminService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminService = adminService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = String.valueOf(request.getAttribute("accessToken"));

        Long id = Long.parseLong(jwtTokenProvider.getPrincipal(token));

        return adminService.isAdmin(id);
    }
}
