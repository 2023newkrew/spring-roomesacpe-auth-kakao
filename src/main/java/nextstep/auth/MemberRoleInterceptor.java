package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.member.Member;
import nextstep.member.MemberRole;
import nextstep.member.MemberService;
import nextstep.support.AuthorizationException;
import nextstep.util.AuthenticationUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MemberRoleInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public MemberRoleInterceptor(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = AuthenticationUtil.extractToken(authorization);
        validateAuthorization(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private void validateAuthorization(String token) {
        Long principal = Long.parseLong(getPrincipalFromToken(token));
        Member member = memberService.findById(principal);
        if(member.getRole() != MemberRole.ADMIN) {
            throw new AuthorizationException();
        }
    }

    private String getPrincipalFromToken(String token) {
        return jwtTokenProvider.getPrincipal(token);
    }
}
