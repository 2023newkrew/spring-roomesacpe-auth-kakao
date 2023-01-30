package nextstep.support.interceptor;

import nextstep.support.Role;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.Member;
import nextstep.member.MemberService;
import nextstep.support.exception.InterceptorExcpetion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminHandlerInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminHandlerInterceptor(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new InterceptorExcpetion("invalid token", HttpStatus.UNAUTHORIZED);
        }

        Member member = memberService.findById(Long.parseLong(jwtTokenProvider.getPrincipal(token)));

        if (member.getRole() != Role.ADMIN) {
            throw new InterceptorExcpetion("forbidden access", HttpStatus.FORBIDDEN);
        }

        return true;
    }
}
