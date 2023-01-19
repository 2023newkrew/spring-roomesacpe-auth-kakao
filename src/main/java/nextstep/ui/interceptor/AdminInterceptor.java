package nextstep.ui.interceptor;

import nextstep.member.MemberRole;
import nextstep.support.ForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // role이 ADMIN인지 판별
        MemberRole role = (MemberRole) request.getAttribute("role");
        if (!role.equals(MemberRole.ADMIN)) throw new ForbiddenException();

        return super.preHandle(request, response, handler);
    }

}
