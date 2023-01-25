package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.exception.UnauthorizedException;
import nextstep.member.MemberDao;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorizationValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationValue == null || !authorizationValue.startsWith("Bearer ")) {
            throw new UnauthorizedException();
        }

        String token = authorizationValue.substring("Bearer ".length());
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }

        long id = Long.parseLong(jwtTokenProvider.getPrincipal(token));

        if (!memberDao.findById(id).isAdmin()) {
            throw new UnauthorizedException();
        }

        return true;
    }
}
