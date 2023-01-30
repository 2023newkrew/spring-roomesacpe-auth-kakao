package nextstep.config;

import nextstep.auth.JwtTokenProvider;
import nextstep.config.auth.Auth;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
    private final JwtTokenProvider provider;
    private final MemberDao memberDao;

    public AdminInterceptor(JwtTokenProvider provider, MemberDao memberDao) {
        this.provider = provider;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        if (auth == null) {
            return true;
        }

        HttpSession session = request.getSession();
        if (session == null) {
            throw new UnAuthorizedException();
        }

        String accessToken = request.getHeader("Authorization");
        if (accessToken == null) {
            throw new UnAuthorizedException();
        }

        Long userId = Long.parseLong(provider.getPrincipal(accessToken));
        String role = provider.getRole(accessToken);

        Member member = memberDao.findById(userId);

        if ("ADMIN".equals(role)) {
            if (Objects.equals(member.getName(), "admin")) {
                return true;
            }

            throw new UnAuthorizedException();
        }

        if ("USER".equals(role)) {
            return true;
        }

        return super.preHandle(request, response, handler);
    }
}