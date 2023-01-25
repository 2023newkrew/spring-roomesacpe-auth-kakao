package nextstep.support;

import nextstep.auth.util.AuthorizationTokenExtractor;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.InvalidAuthorizationTokenException;
import nextstep.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
        String token = AuthorizationTokenExtractor.extract(
                request.getHeader(AuthorizationTokenExtractor.AUTHORIZATION))
                .orElseThrow(() -> new InvalidAuthorizationTokenException(ErrorCode.INVALID_TOKEN));

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidAuthorizationTokenException(ErrorCode.TOKEN_EXPIRED);
        }

        String username = jwtTokenProvider.getPrincipal(token);

        Optional<Member> member = memberDao.findByUsername(username);

        if (member.isEmpty() || member.get().getRole() != Role.ADMIN) {
            throw new NotExistEntityException(ErrorCode.USER_NOT_FOUND);
        }

        return true;
    }
}
