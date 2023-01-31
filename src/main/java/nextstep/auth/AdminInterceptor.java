package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.exception.UnauthorizedException;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;
    private final PrincipalExtractor principalExtractor;

    public AdminInterceptor(MemberDao memberDao, PrincipalExtractor principalExtractor) {
        this.memberDao = memberDao;
        this.principalExtractor = principalExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long id = principalExtractor.extract(request);

        if (!memberDao.findById(id).isAdmin()) {
            throw new UnauthorizedException();
        }

        return true;
    }
}
