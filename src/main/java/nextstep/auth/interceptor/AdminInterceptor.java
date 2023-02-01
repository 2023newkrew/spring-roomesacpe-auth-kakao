package nextstep.auth.interceptor;

import lombok.RequiredArgsConstructor;
import nextstep.auth.LoginMemberContextHolder;
import nextstep.auth.domain.LoginMember;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final LoginMemberContextHolder loginMemberContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginMember ctx = loginMemberContextHolder.getContext();
        if (ctx == null || !ctx.isAdmin()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
