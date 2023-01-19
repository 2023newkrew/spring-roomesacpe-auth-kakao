package nextstep.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.AuthService;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.member.Member;
import nextstep.member.MemberService;

public class AdminInterceptor extends LoginInterceptor {

    public AdminInterceptor(AuthService authService, MemberService memberService) {
        super(authService, memberService);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!super.preHandle(request, response, handler)) {
            return false;
        }
        Member member = (Member) request.getAttribute("member");
        if (!member.isAdmin()) {
            throw new RoomReservationException(ErrorCode.ADMIN_AUTHORITY_REQUIRED);
        }
        return true;
    }
}
