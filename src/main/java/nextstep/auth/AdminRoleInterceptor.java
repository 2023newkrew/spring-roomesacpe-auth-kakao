package nextstep.auth;

import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.member.Role;
import nextstep.support.NotExistEntityException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminRoleInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider tokenProvider;
    private final MemberService memberService;

    public AdminRoleInterceptor(JwtTokenProvider tokenProvider, MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request).orElseThrow(() -> new AuthenticationException("Log in required."));
        if (!tokenProvider.validateToken(token)) {
            throw new AuthenticationException("Token invalid.");
        }
        Member loggedInMember = memberService.findById(Long.parseLong(tokenProvider.getPrincipal(token)));
        if (loggedInMember == null) {
            throw new NotExistEntityException();
        }
        if (!loggedInMember.getRole().equals(Role.ADMIN)) {
            throw new AuthenticationException("Access not allowed.");
        }
        request.setAttribute("loggedInMember", loggedInMember);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
