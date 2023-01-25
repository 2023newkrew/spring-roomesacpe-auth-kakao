package nextstep.infrastructure.web;

import nextstep.domain.member.MemberRole;
import nextstep.interfaces.AuthorizationException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = extractToken(request);
        jwtTokenProvider.validateToken(token);

        String memberId = jwtTokenProvider.getMemberId(token);
        MemberRole role = MemberRole.valueOf(jwtTokenProvider.getRole(token));
        if (memberId == null || !role.equals(MemberRole.ADMIN)) {
            throw new AuthorizationException();
        }

        return true;
    }

    private String extractToken(HttpServletRequest request) {
        try {
            return AuthorizationExtractor.extract(request);
        } catch (Exception e) {
            throw new AuthorizationException();
        }
    }
}
