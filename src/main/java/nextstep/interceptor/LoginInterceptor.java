package nextstep.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.AuthService;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    private final MemberService memberService;

    public LoginInterceptor(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = request.getHeader("authorization");
        if (accessToken == null) {
            throw new RoomReservationException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        String principal = authService.getPrincipal(accessToken.split(" ")[1]);
        Member member = memberService.findById(Long.parseLong(principal));
        request.setAttribute("member", member);
        return true;
    }

}
