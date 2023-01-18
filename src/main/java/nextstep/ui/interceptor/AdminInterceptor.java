package nextstep.ui.interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberRole;
import nextstep.support.ForbiddenException;
import nextstep.support.TokenExpirationException;
import nextstep.support.UnauthorizedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String header = request.getHeader("authorization");
        if (header == null) throw new UnauthorizedException();

        String token = header.substring("Bearer ".length());
        if (!jwtTokenProvider.validateToken(token)) throw new TokenExpirationException();
        if (!jwtTokenProvider.getRole(token).equals(MemberRole.ADMIN.toString())) throw new ForbiddenException();

        return super.preHandle(request, response, handler);
    }

}
