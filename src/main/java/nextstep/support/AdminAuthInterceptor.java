package nextstep.support;

import nextstep.admin.Admin;
import nextstep.admin.AdminDao;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminDao adminDao;

    public AdminAuthInterceptor(JwtTokenProvider jwtTokenProvider, AdminDao adminDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminDao = adminDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationTokenExtractor.extract(
                request.getHeader(AuthorizationTokenExtractor.AUTHORIZATION))
                .orElseThrow(() -> new InvalidAuthorizationTokenException(ErrorCode.INVALID_TOKEN));

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = jwtTokenProvider.getPrincipal(token);

        if (adminDao.findByUsername(username).isEmpty()) {
            throw new NotExistEntityException(ErrorCode.USER_NOT_FOUND);
        }

        return true;
    }
}
