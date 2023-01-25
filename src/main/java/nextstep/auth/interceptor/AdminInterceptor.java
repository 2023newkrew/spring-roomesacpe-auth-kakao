package nextstep.auth.interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.exception.business.BusinessException;
import nextstep.exception.business.BusinessErrorCode;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.auth.role.Role.ROLE_ADMIN;

public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        if (hasAdminRole(accessToken)) {
            return true;
        }
        throw new BusinessException(BusinessErrorCode.ACCESS_DENIED);
    }

    private boolean hasAdminRole(String accessToken) {
        return jwtTokenProvider.getRoles(accessToken)
                .stream()
                .anyMatch(role -> role.equals(ROLE_ADMIN.name()));
    }
}
