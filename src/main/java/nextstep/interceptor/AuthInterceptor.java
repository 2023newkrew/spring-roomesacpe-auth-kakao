package nextstep.interceptor;

import nextstep.auth.AccessType;
import nextstep.auth.JwtTokenProvider;
import nextstep.exception.BusinessException;
import nextstep.exception.ErrorCode;
import nextstep.member.MemberRole;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        MemberRole loginMemberRole = jwtTokenProvider.getRole(parseToken(request.getHeader("Authorization")));//현재 로그인한 멤버의 권한

        MemberRole accessRole = ((HandlerMethod) handler).getMethodAnnotation(AccessType.class).role(); //접근 권한

        if (accessRole == MemberRole.ADMIN && loginMemberRole == MemberRole.ADMIN)
            return true;

        if (accessRole == MemberRole.USER && loginMemberRole != null)
            return true;

        throw new BusinessException(ErrorCode.NOT_AUTHENTICATED);
    }

    private String parseToken(String authHeader) {
        return authHeader.substring(6).trim();
    }

}
