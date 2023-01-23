package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.member.Member;
import nextstep.member.MemberRole;
import nextstep.member.MemberService;
import nextstep.support.AuthorityException;
import nextstep.support.AuthenticationUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthorityInterceptor(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = AuthenticationUtil.extractToken(authorization);
        validateAuthority(token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private void validateAuthority(String token) {
        Long principal = Long.parseLong(getPrincipalFromToken(token));
        Member member = memberService.findById(principal);
        if(member.getRole() != MemberRole.ADMIN) {
            throw new AuthorityException();
        }
    }

    private String getPrincipalFromToken(String token) {
        return jwtTokenProvider.getPrincipal(token);
    }
}
