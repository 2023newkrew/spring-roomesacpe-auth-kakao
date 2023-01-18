package nextstep.support.interceptor;

import nextstep.domain.Member;
import nextstep.service.MemberService;
import nextstep.support.util.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AdminHandlerInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminHandlerInterceptor(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String accessToken = request.getHeader("Authorization");

        if (Objects.isNull(accessToken) || !jwtTokenProvider.validateToken(accessToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        long memberId = Long.parseLong(jwtTokenProvider.getPrincipal(accessToken));
        Member member = memberService.findByMemberId(memberId);

        if (!member.isAdmin()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }
}
