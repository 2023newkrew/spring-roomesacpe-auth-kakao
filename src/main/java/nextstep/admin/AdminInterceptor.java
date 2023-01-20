package nextstep.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nextstep.auth.JwtTokenProvider;
import nextstep.infrastructure.AuthorizationExtractor;
import nextstep.support.exception.UnauthorizedException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthorizationExtractor.extract(request);
        if (Boolean.FALSE.equals(jwtTokenProvider.isAdmin(accessToken))) {
            throw new UnauthorizedException("관리자 권한이 없습니다.");
        }
        return true;
    }
}
