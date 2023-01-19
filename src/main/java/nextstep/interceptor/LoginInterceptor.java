package nextstep.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthService;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    private final MemberService memberService;

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization");
        if (accessToken == null) {
            throw new RoomReservationException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        return accessToken.split(" ")[1];
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = extractAccessTokenFromHeader(request);
        String principal = authService.getPrincipal(accessToken);
        Member member = memberService.findById(Long.parseLong(principal));
        request.setAttribute("member", member);
        return true;
    }
}
