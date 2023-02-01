package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import nextstep.support.AuthorizationException;
import nextstep.support.ForbiddenException;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        NeedAuth needAuth = ((HandlerMethod) handler).getMethodAnnotation(NeedAuth.class);

        if (needAuth == null) {
            return true;
        }

        String accessToken = AuthorizationExtractor.extract(request)
                .orElseThrow(() -> new AuthorizationException("인증이 필요합니다."));
        boolean validationResult = jwtTokenProvider.validateToken(accessToken);
        if (!validationResult) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }

        if (needAuth.role().equals(Role.ADMIN)) {
            Long userId = Long.parseLong(jwtTokenProvider.getPrincipal(accessToken));
            Member member = memberDao.findById(userId)
                    .orElseThrow(NotExistEntityException::new);
            if (!member.isAdmin()) {
                throw new ForbiddenException("접근 권한이 없습니다.");
            }
        }
        return true;
    }
}
