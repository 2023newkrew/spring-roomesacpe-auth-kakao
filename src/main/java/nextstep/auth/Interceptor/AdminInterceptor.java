package nextstep.auth.Interceptor;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.member.Role;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.auth.Interceptor.LoginInterceptor.authorization;
import static nextstep.support.Messages.EMPTY_TOKEN;
import static nextstep.support.Messages.NOT_ALLOWED_SERVICE;

public class AdminInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminInterceptor(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader(authorization);
        if (accessToken == null) {
            throw new AuthorizationServiceException(EMPTY_TOKEN.getMessage());
        }
        String username = jwtTokenProvider.getPrincipal(accessToken);
        Member member = memberService.findByUsername(username).get(0);
        if (!member.getRole().equals(Role.ADMIN)) {
            throw new AuthorizationServiceException(NOT_ALLOWED_SERVICE.getMessage());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
