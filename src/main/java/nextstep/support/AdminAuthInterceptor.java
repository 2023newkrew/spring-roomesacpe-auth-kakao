package nextstep.support;

import nextstep.auth.Auth;
import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import nextstep.exception.UnauthorizedMemberException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AdminAuthInterceptor(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Auth auth = extractAuthAnnotation(handler);
        if (auth == null || auth.role() != Role.ADMIN) {
            return true;
        }

        String token = AuthorizationTokenExtractor.extract(
                request.getHeader(AuthorizationTokenExtractor.AUTHORIZATION))
                .orElseThrow(() -> new InvalidAuthorizationTokenException(ErrorCode.INVALID_TOKEN));

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = jwtTokenProvider.getPrincipal(token);

        Member member = memberDao.findByUsername(username)
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.UNAUTHORIZED));

        if (member.getRole() != Role.ADMIN) {
            throw new UnauthorizedMemberException(ErrorCode.UNAUTHORIZED);
        }

        return true;
    }

    private Auth extractAuthAnnotation(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(Auth.class);
    }
}
