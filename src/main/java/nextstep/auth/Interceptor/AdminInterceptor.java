package nextstep.auth.Interceptor;

import nextstep.member.Member;
import nextstep.member.Role;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static nextstep.support.Messages.*;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Member> memberList = TokenVerifier.validateToken(request);
        if (!memberList.get(0).getRole().equals(Role.ADMIN)) {
            throw new AuthorizationServiceException(NOT_ALLOWED_SERVICE.getMessage());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
