package nextstep.admin;

import nextstep.auth.AuthType;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.exception.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    @Autowired
    public AdminInterceptor(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractToken(request);
        Long id = Long.parseLong(jwtTokenProvider.getPrincipal(token));
        Member member = memberDao.findById(id);

        if(!member.isAdmin())
            throw new AuthorizationException();

        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader.startsWith(AuthType.BEARER))
            return authHeader.split(" ")[1];
        return null;
    }
}
