package nextstep.common;

import static nextstep.common.exception.ExceptionMessage.ADMIN_ONLY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthorizationExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.common.exception.UnauthorizedException;
import nextstep.member.MemberRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        validAdminToken(request);
        return true;
    }

    private void validAdminToken(HttpServletRequest request) {
        String token = authorizationExtractor.extract(request);
        MemberRole memberRole = jwtTokenProvider.getRole(token);
        if (memberRole != MemberRole.ADMIN) {
            throw new UnauthorizedException(ADMIN_ONLY.getMessage());
        }
    }
}
